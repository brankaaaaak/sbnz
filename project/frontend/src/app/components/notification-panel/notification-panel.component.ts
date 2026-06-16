import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export interface SystemAlert {
  type: string;
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
    const notification: AlertNotification = {
      id: ++this.idCounter,
      type: alert.type,
      message: this.formatMessage(alert.type),
      severity: this.getSeverity(alert.type),
      time: new Date(),
      read: false
    };

    // Newest first
    this.notifications.unshift(notification);

    // Auto-open panel on critical alerts
    if (notification.severity === 'critical') {
      this.isOpen = true;
    }

    // Cap at 50 notifications
    if (this.notifications.length > 50) {
      this.notifications = this.notifications.slice(0, 50);
    }
  }

  private formatMessage(type: string): string {
    const messages: Record<string, string> = {
      'HIGH_CALL_VOLUME':   '5+ poziva u posljednjih 10 minuta',
      'RED_SPIKE':          '3+ crvena slučaja u posljednjih 10 minuta',
      'SYSTEM_OVERLOAD':    'Visok broj poziva + kritičnih pacijenata',
      'SLOW_BURN':          '8+ poziva u posljednjih 30 minuta',
      'MIXED_SEVERITY':     'Visok mješoviti prioritet (crveni + žuti)',
      'CRISIS_MODERATE':    'Krizna situacija — umjerena ozbiljnost',
      'CRISIS_HIGH':        'Krizna situacija — visoka ozbiljnost',
      'CRISIS_CRITICAL':    'KRITIČNA KRIZNA SITUACIJA',
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