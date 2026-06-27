#!/usr/bin/env python3

"""
curl -X POST "http://localhost:8080/api/test/alert?type=CRISIS_CRITICAL"
curl -X POST "http://localhost:8080/api/test/alert?type=RED_SPIKE"
curl -X POST "http://localhost:8080/api/test/alert?type=HIGH_CALL_VOLUME"
"""

import requests
import time
import sys
import random

BASE_URL = "http://localhost:8080/api"


def create_call(incident_type, systolic, diastolic, pulse, temp, conscious, breathing_regular=True):
    """
    Poziva /api/assessment/preliminary sa podacima o pozivu.
    Vraća dict sa callId i preliminaryLevel.
    """
    payload = {
        "incidentType": incident_type,
        "patient": {
            "age": random.randint(20, 60),
            "vitalSigns": {
                "systolicPressure": systolic,
                "diastolicPressure": diastolic,
                "pulse": pulse,
                "temperature": temp,
                "consciousnessLevel": conscious,
                "breathingRegular": breathing_regular
            }
        }
    }
    resp = requests.post(f"{BASE_URL}/assessment/preliminary", json=payload)
    resp.raise_for_status()
    return resp.json()  # {callId, incidentType, preliminaryLevel, reasons}

def add_symptoms(call_id, incident_type, symptoms_dict):
    """
    Poziva /api/assessment/symptoms sa datim simptomima.
    symptoms_dict je dict sa ključevima kao što su 'choking', 'openWound', itd.
    """
    payload = {
        "callId": call_id,
        "symptoms": symptoms_dict
    }
    resp = requests.post(f"{BASE_URL}/assessment/symptoms", json=payload)
    resp.raise_for_status()
    return resp.json()  # {callId, finalLevel}

def wait_with_progress(seconds, message="Čekam..."):
    """Prikaže odbrojavanje."""
    print(f"{message} ({seconds} sekundi)")
    for i in range(seconds, 0, -1):
        sys.stdout.write(f"\rPreostalo: {i} s  ")
        sys.stdout.flush()
        time.sleep(1)
    print("\nGotovo.")

# scenariji 

def scenario_high_call_volume():
    """Aktivira HIGH_CALL_VOLUME: 5 poziva u prozoru."""
    print("\n=== SCENARIO: HIGH_CALL_VOLUME ===")
    for i in range(5):
        resp = create_call("STING", 120, 80, 70, 36.5, "CONSCIOUS", True)
        print(f"  Poziv {i+1}: ID={resp['callId']}, preliminarni={resp['preliminaryLevel']}")
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        time.sleep(5)  

def scenario_red_spike():
    """Aktivira RED_SPIKE: 3 RED assessment-a u prozoru."""
    print("\n=== SCENARIO: RED_SPIKE ===")
    for i in range(3):
        # STING + UNCONSCIOUS_UNRESPONSIVE -> preliminarni RED 
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        print(f"  Poziv {i+1}: ID={resp['callId']}, preliminarni={resp['preliminaryLevel']}")
        # Dodajemo simptom koji finalizuje RED (npr. choking)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        time.sleep(5)

def scenario_system_overload():
    """Aktivira SYSTEM_OVERLOAD: 5 poziva + 2 RED u prozoru."""
    print("\n=== SCENARIO: SYSTEM_OVERLOAD ===")
    # 2 RED
    for i in range(2):
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        print(f"  RED poziv {i+1}: ID={resp['callId']}")
        time.sleep(5)
    # 3 zelena
    for i in range(3):
        resp = create_call("STING", 120, 80, 70, 36.5, "CONSCIOUS", True)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  Zeleni poziv {i+3}: ID={resp['callId']}")
        time.sleep(5)

def scenario_mixed_severity():
    """Aktivira MIXED_SEVERITY: 2 RED + 3 YELLOW u prozoru."""
    print("\n=== SCENARIO: MIXED_SEVERITY ===")
    # 2 RED (STING + nesvjestan)
    for i in range(2):
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        print(f"  RED {i+1}: ID={resp['callId']}")
        time.sleep(5)
    # 3 YELLOW (STING + svjestan + nepravilno disanje -> preliminarni YELLOW, plus simptomi da finalizuje YELLOW)
    for i in range(3):
        resp = create_call("STING", 120, 80, 100, 37.0, "CONSCIOUS", False)  # nepravilno disanje -> YELLOW
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  YELLOW {i+1}: ID={resp['callId']}")
        time.sleep(5)

def scenario_crisis():
    """Aktivira CRISIS (scoring)."""
    print("\n=== SCENARIO: CRISIS (scoring) ===")
    # Treba da postignemo skor >=10 u 15 min: 3*red + yellow + calls >=10
    # Npr. 2 RED (6 poena) + 2 YELLOW (2 poena) + 3 calls (3 poena) = 11
    # Ukupno 7 događaja.
    for i in range(2):
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        print(f"  RED {i+1}: ID={resp['callId']}")
        time.sleep(5)
    for i in range(2):
        resp = create_call("STING", 120, 80, 100, 37.0, "CONSCIOUS", False)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  YELLOW {i+1}: ID={resp['callId']}")
        time.sleep(5)
    for i in range(3):
        resp = create_call("STING", 120, 80, 70, 36.5, "CONSCIOUS", True)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  Zeleni {i+1}: ID={resp['callId']}")
        time.sleep(5)

def scenario_accelerating_call_trend():
    """Aktivira ACCELERATING_CALL_TREND: 6 poziva brzo, od toga 4+ u zadnjih 5 min."""
    print("\n=== SCENARIO: ACCELERATING_CALL_TREND ===")
    for i in range(6):
        resp = create_call("STING", 120, 80, 70, 36.5, "CONSCIOUS", True)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  Brzi poziv {i+1}: ID={resp['callId']}")
        time.sleep(10)

def scenario_high_average_severity():
    """Aktivira HIGH_AVERAGE_SEVERITY: 4 finalizovana slucaja sa prosjekom ozbiljnosti >= 2.3."""
    print("\n=== SCENARIO: HIGH_AVERAGE_SEVERITY ===")
    # 2 RED + 2 YELLOW => prosjek severityScore = (3 + 3 + 2 + 2) / 4 = 2.5
    for i in range(2):
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        print(f"  RED {i+1}: ID={resp['callId']}")
        time.sleep(5)

    for i in range(2):
        resp = create_call("STING", 120, 80, 100, 37.0, "CONSCIOUS", False)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  YELLOW {i+1}: ID={resp['callId']}")
        time.sleep(5)

def scenario_high_red_ratio():
    """Aktivira HIGH_RED_RATIO: RED slucajevi su najmanje 50% finalizovanih slucajeva."""
    print("\n=== SCENARIO: HIGH_RED_RATIO ===")
    # 2 RED + 2 GREEN => RED ratio = 2 / 4 = 0.5
    for i in range(2):
        resp = create_call("STING", 100, 70, 90, 37.0, "UNCONSCIOUS_UNRESPONSIVE", True)
        add_symptoms(resp['callId'], "STING", {"choking": True, "systemicSwelling": False, "skinReaction": False, "previousSevereReaction": False})
        print(f"  RED {i+1}: ID={resp['callId']}")
        time.sleep(5)

    for i in range(2):
        resp = create_call("STING", 120, 80, 70, 36.5, "CONSCIOUS", True)
        add_symptoms(resp['callId'], "STING", {"skinReaction": False, "choking": False, "systemicSwelling": False, "previousSevereReaction": False})
        print(f"  GREEN {i+1}: ID={resp['callId']}")
        time.sleep(5)

def main():
    print("===== CEP TEST SKRIPTA (prirodno aktiviranje) =====")
    print("")
    print("1 - HIGH_CALL_VOLUME (5 poziva u 10 min)")
    print("2 - RED_SPIKE (3 RED u 10 min)")
    print("3 - SYSTEM_OVERLOAD (5 poziva + 2 RED u 10 min)")
    print("4 - MIXED_SEVERITY (2 RED + 3 YELLOW u 10 min)")
    print("5 - CRISIS (skor >=10 u 15 min)")
    print("6 - ACCELERATING_CALL_TREND (trend rasta poziva)")
    print("7 - HIGH_AVERAGE_SEVERITY (visok prosjek ozbiljnosti)")
    print("8 - HIGH_RED_RATIO (visok procenat RED slucajeva)")
    choice = input("Unesi broj: ").strip()

    if choice == "1":
        scenario_high_call_volume()
    elif choice == "2":
        scenario_red_spike()
    elif choice == "3":
        scenario_system_overload()
    elif choice == "4":
        scenario_mixed_severity()
    elif choice == "5":
        scenario_crisis()
    elif choice == "6":
        scenario_accelerating_call_trend()
    elif choice == "7":
        scenario_high_average_severity()
    elif choice == "8":
        scenario_high_red_ratio()
    else:
        print("Error")

if __name__ == "__main__":
    main()

"""
promijeniti u drl na kraći vremenski prozor 1 m
"""
