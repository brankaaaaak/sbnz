import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export interface SystemAlert {
  type: string;
  active?: boolean;
  timestamp?: string;
}

interface AlertNotification {
  id: number;
  type: string;
  message: string;
  severity: 'critical' | 'high' | 'medium';
  time: Date;
  read: boolean;
}

@Component({
  selector: 'app-notification-panel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notification-panel.component.html',
  styleUrls: ['./notification-panel.component.scss']
})
export class NotificationPanelComponent implements OnInit, OnDestroy {

  isOpen = false;
  notifications: AlertNotification[] = [];
  private stompClient!: Client;
  private idCounter = 0;

  get unreadCount(): number {
    return this.notifications.filter(n => !n.read).length;
  }

  ngOnInit(): void {
    this.connectWebSocket();
  }

  ngOnDestroy(): void {
    if (this.stompClient?.active) {
      this.stompClient.deactivate();
    }
  }

  private connectWebSocket(): void {
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      onConnect: () => {
        this.stompClient.subscribe('/topic/alerts', (msg: IMessage) => {
          const alert: SystemAlert = JSON.parse(msg.body);
          this.handleAlert(alert);
        });
      }
    });

    this.stompClient.activate();
  }

  private handleAlert(alert: SystemAlert): void {

    if (alert.active === false) {
      this.removeAlert(alert.type);
      return;
    }

    // Za CRISIS upgrade — ako postoji stariji CRISIS alert nižeg nivoa, ukloni ga
    if (alert.type.startsWith('CRISIS_')) {
      this.notifications = this.notifications.filter(
        n => !n.type.startsWith('CRISIS_')
      );
    }

    const notification: AlertNotification = {
      id: ++this.idCounter,
      type: alert.type,
      message: this.formatMessage(alert.type),
      severity: this.getSeverity(alert.type),
      time: new Date(),
      read: false
    };

    this.notifications.unshift(notification);

    if (notification.severity === 'critical') {
      this.isOpen = true;
    }

    if (this.notifications.length > 50) {
      this.notifications = this.notifications.slice(0, 50);
    }

    // Auto-expire
    // setTimeout(() => {
    //   this.notifications = this.notifications.filter(n => n.id !== notification.id);
    // }, 1 * 60 * 1000);
  }

  private removeAlert(type: string): void {
    if (type.startsWith('CRISIS_')) {
      this.notifications = this.notifications.filter(
        n => !n.type.startsWith('CRISIS_')
      );
      return;
    }

    this.notifications = this.notifications.filter(n => n.type !== type);
  }

  private formatMessage(type: string): string {
    const messages: Record<string, string> = {
      'HIGH_CALL_VOLUME': '5+ calls in the last 10 minutes',
      'RED_SPIKE': '3+ red-priority cases in the last 10 minutes',
      'SYSTEM_OVERLOAD': 'High call volume + critical patients',
      'SLOW_BURN': '8+ calls in the last 30 minutes',
      'MIXED_SEVERITY': 'High mixed-priority load (red + yellow)',
      'CRISIS_MODERATE': 'Crisis situation — moderate severity',
      'CRISIS_HIGH': 'Crisis situation — high severity',
      'CRISIS_CRITICAL': 'CRITICAL CRISIS SITUATION',
    };
    return messages[type] ?? `Sistemski alert: ${type}`;
  }

  private getSeverity(type: string): 'critical' | 'high' | 'medium' {
    if (type.includes('CRISIS_CRITICAL') || type === 'SYSTEM_OVERLOAD') return 'critical';
    if (type.includes('CRISIS') || type === 'RED_SPIKE' || type === 'MIXED_SEVERITY') return 'high';
    return 'medium';
  }

  togglePanel(): void {
    this.isOpen = !this.isOpen;
    if (this.isOpen) this.markAllRead();
  }

  markAllRead(): void {
    this.notifications.forEach(n => n.read = true);
  }

  clearAll(): void {
    //this.notifications = [];
    this.isOpen = !this.isOpen;
  }

  trackById(_: number, item: AlertNotification): number {
    return item.id;
  }
}
