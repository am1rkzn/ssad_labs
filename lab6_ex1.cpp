#include <iostream>;
#include <string.h>
using namespace std;


template <typename T>
class Wrapped {
public: T getValue(T x) {
		return x;
	}
};

template <>
class Wrapped <const char*> {
public: int getvalue(const char* x) {
	return strlen(x);
	}
};

int main() {
	Wrapped<int> n;
	Wrapped<double> d;
	int n_1 = n.getValue(5);
	double d_1 = d.getValue(3.14);
	Wrapped<const char*> str;
	int len = str.getvalue("Hello world");
	cout << n_1 << " " << d_1 << " " << len << endl;
}
