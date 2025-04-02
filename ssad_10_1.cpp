#include <iostream>

using namespace std;

// Facade design pattern task

//lights class
class Lights{
private:
    bool lights_state;
public:
    Lights() {
        lights_state = false;
    }
    bool check_ON_State(){
        if (lights_state){
            return true;
        }
        return false;
    }
    void on(){
        if (check_ON_State()){
            cout << "Lights are already on" << endl;
        } else{
            lights_state = true;
            cout << "Lights have been turned on" << endl;
        }
    }
    void off(){
        if (!check_ON_State()){
            cout << "Lights are already off" << endl;
        } else{
            lights_state = false;
            cout << "Lights have been turned off" << endl;
        }
    }
    bool getState(){
        return lights_state;
    }
};

//thermostat class
class Thermostat{
private:
    int temp;
public:
    Thermostat(){
        temp = 0;
    }
    const int comf_temp = 25;
    const int eco_temp = 15;
    void setTemperature(int new_temp){
        temp = new_temp;
        cout << "The temperature has been set to " << temp << endl;
    }
    int getTemp(){
        return temp;
    }
};

//security camera class
class SecurityCamera{
private:
    bool camerasState;
public:
    SecurityCamera(){
        camerasState = false;
    }
    bool check_ON_State(){
        if (camerasState){
            return true;
        }
        return false;
    }
    void activate(){
        if (check_ON_State()){
            cout << "Cameras are already on" << endl;
        } else{
            camerasState = true;
            cout << "Cameras have been activated" << endl;
        }
    }
    void deactivate(){
        if (!check_ON_State()){
            cout << "Cameras are already off" << endl;
        } else{
            camerasState = false;
            cout << "Cameras have been deactivated" << endl;
        }
    }
    bool getState(){
        return camerasState;
    }

};

//SmartHome Facade class
class SmartHomeFacade{
public:
    Lights homeLights;
    Thermostat homeThermostat;
    SecurityCamera homeCameras;
    void leaveHome(){
        homeLights.off();
        homeThermostat.setTemperature(homeThermostat.eco_temp);
        homeCameras.deactivate();
        cout << "Owner has leaved home" << endl;
    }
    void arrivingHome(){
        homeLights.on();
        homeThermostat.setTemperature(homeThermostat.comf_temp);
        homeCameras.deactivate();
        cout << "Owner has arrived home" << endl;
    }
};

int main(){
    SmartHomeFacade smarthome = SmartHomeFacade();
    smarthome.homeThermostat.setTemperature(30);
    smarthome.homeCameras.deactivate();
    smarthome.leaveHome();
    smarthome.homeLights.on();
    smarthome.arrivingHome();
}
