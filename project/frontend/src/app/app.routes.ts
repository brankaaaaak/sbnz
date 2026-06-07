import { Routes } from '@angular/router';
import { WelcomePageComponent } from './components/welcome-page/welcome-page.component';
import { NewCallComponent } from './components/new-call/new-call.component';
export const routes: Routes = [
    { path: '', component: WelcomePageComponent },
    { path: 'new-patient', component: NewCallComponent }
];
