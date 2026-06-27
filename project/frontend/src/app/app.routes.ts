import { Routes } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { NewCallComponent } from './components/new-call/new-call.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
export const routes: Routes = [
    { path: '', component: WelcomePageComponent },
    { path: 'new-patient', component: NewCallComponent },
    { path: 'dashboard', component: DashboardComponent}
];
