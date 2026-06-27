import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SystemStats } from '../../models/models';
import { interval } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  calls: any[] = [];
  stats?: SystemStats;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadCalls();
    this.loadStats();

      interval(20000).subscribe(() => {
      this.loadCalls();
      this.loadStats();
    });
  }

  loadCalls() {
    this.http
      .get<any[]>('http://localhost:8080/api/calls')
      .subscribe(res => {
        this.calls = res;
      });
  }

  loadStats() {
    this.http
      .get<SystemStats>('http://localhost:8080/api/stats')
      .subscribe(res => {
        this.stats = res;
      });
  }

  finishCall(id: number) {

    this.http
      .delete(
        `http://localhost:8080/api/calls/${id}/finish`,
        {}
      )
      .subscribe(() => {
        this.loadCalls();
        this.loadStats();
      });
  }
}