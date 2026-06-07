import { CommonModule, NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-call',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-call.component.html',
  styleUrl: './new-call.component.scss'
})
export class NewCallComponent implements OnInit{

  step = 1;

  call: any = {
    location: '',
    incidentType: null,
    patient: {
      name: '',
      age: 0,
      vitalSigns: {
        pulse: 0,
        systolicPressure: 0,
        diastolicPressure: 0,
        temperature: 36.5,
        breathingRegular: true,
        consciousnessLevel: null
      }
    }
  };

  incidentTypes: string[] = [];
  consciousnessLevels: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadEnums();
  }

  loadEnums() {

    this.http.get<string[]>('http://localhost:8080/api/enums/incident-types')
      .subscribe(data => this.incidentTypes = data);

    this.http.get<string[]>('http://localhost:8080/api/enums/consciousness-levels')
      .subscribe(data => this.consciousnessLevels = data);
  }

  submit() {
    this.http.post('http://localhost:8080/api/calls', this.call)
      .subscribe(res => {
        console.log('Saved', res);
      });
  }

}
