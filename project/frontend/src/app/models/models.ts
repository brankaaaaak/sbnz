export interface PreliminaryResult {
  callId: number;
  incidentType: string;
  preliminaryLevel: string;
  reasons: string[];
}

export interface FinalResult {
  finalLevel: string;
  reasons: string[];
  callId: number;
}

export interface SystemStats {
  activeCalls: number;
  criticalCases: number;
}