#include <iostream>

using namespace std;
void count_time(int time) {
    int time_h = time / 3600;
    time  = time % 3600;
    int time_m = time / 60;
    time  = time % 60;
    int time_s = time;
    cout << time_h << ":" << time_m << ":" << time_s << endl;
}
int main() {
    int time;
    cin >> time;
    count_time(time);
}
