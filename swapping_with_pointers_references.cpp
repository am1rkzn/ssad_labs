#include <iostream>

using namespace std;

void swap_with_pointers(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}
void swap_with_reference(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
}
int main() {
    int n1, n2;
    cin >> n1 >> n2;
    swap_with_pointers(&n1, &n2);
    cout << n1 << " " << n2 << endl;
    swap_with_reference(n1,n2);
    cout << n1 << " " << n2 << endl;
    return 0;
}
