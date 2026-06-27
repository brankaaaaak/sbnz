import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NotificationPanelComponent } from './components/notification-panel/notification-panel.component';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationPanelComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
