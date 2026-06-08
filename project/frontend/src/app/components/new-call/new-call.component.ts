import { CommonModule, NgIf } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FinalResult, PreliminaryResult } from '../../models/models';

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

  preliminaryResult: PreliminaryResult | null = null;
  finalResult: FinalResult | null = null;
  incidentTypes: string[] = [];
  consciousnessLevels: string[] = [];

  symptomSchema: any[] = [];
  symptomValues: any = {};

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
    this.http.post(
      'http://localhost:8080/api/assessment/preliminary',
      this.call
    )
    .subscribe((res:any) => {

      this.preliminaryResult = res;

      this.step = 4;
    });
  }

  loadSymptoms() {
    this.http.get<any[]>(
      `http://localhost:8080/api/symptoms/schema/${this.call.incidentType}`
    ).subscribe(res => {

      this.symptomSchema = res;

      this.symptomValues = {};
      res.forEach(f => this.symptomValues[f.key] = false);

      this.step = 5;
    });
  }

  submitSymptoms() {

    let payload: any = {
      callId: this.preliminaryResult?.callId,
      incidentType: this.preliminaryResult?.incidentType,
      symptoms: {}
    };

    switch (this.call.incidentType) {

      case 'INJURY_HEAD':
        payload.symptoms = {
          vomiting: this.symptomValues.vomiting,
          openWound: this.symptomValues.openWound
        };
        break;

      case 'INJURY_EXTREMITY':
        payload.symptoms = {
          openFracture: this.symptomValues.openFracture,
          bleeding: this.symptomValues.bleeding
        };
        break;

      case 'STING':
        payload.symptoms = {
          choking: this.symptomValues.choking,
          systemicSwelling: this.symptomValues.systemicSwelling,
          skinReaction: this.symptomValues.skinReaction,
          previousSevereReaction: this.symptomValues.previousSevereReaction
        };
        break;

      case 'FAINTING':
        payload.symptoms = {
          diabetes: this.symptomValues.diabetes,
          tookTherapy: this.symptomValues.tookTherapy,
          painEarlier: this.symptomValues.painEarlier
        };
        break;
    }

    this.http.post<any>(
    'http://localhost:8080/api/assessment/symptoms',
    payload
  ).subscribe(res => {

    this.finalResult = res;

    this.step = 6;
  });
  }

}
