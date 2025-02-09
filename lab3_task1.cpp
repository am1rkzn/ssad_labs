#include <iostream>
#include <vector>
using namespace std;

class Animal {
protected:
 string name;
 int age;
public:
 //Animal(string n, int k) : name(n), age(k) {}
 Animal(string n, int a) {
  name = n;
  age = a;
 }
 virtual ~Animal() {
  cout << "Animal " << name << " has been destroyed" << endl;
 }
 virtual void makeSound() const = 0;
};

// intermidiate class for land animals

class landAnimal : virtual public Animal {
public:
 landAnimal(string n, int a): Animal(n,a){}

 virtual void walk() const = 0;
};

class waterAnimal : virtual public Animal {
public:
 waterAnimal(string n, int a): Animal(n,a){}

 virtual void swim() const = 0;
};

// Derived class 

class Lion : public landAnimal {
public:
 Lion(string n, int a): landAnimal(n,a), Animal(n,a){}

 void makeSound() const override {
  cout << "Lion" << " " << name << " " << "roars" << endl;
 }

 void walk() const override {
  cout << "Lion" << " " << name << " " << "walks" << endl;
 }
};

class Dolphin : public waterAnimal {
public:
 Dolphin(string n, int a): waterAnimal(n,a), Animal(n,a){}

 void makeSound() const override {
  cout << "Dolphin" << " " << name << " " << "makes sth strange sound" << endl;
 }

 void swim() const override {
  cout << "Dolphin" << " " << name << " " << "is swimming" << endl;
 }
};

class Frog : public landAnimal, public waterAnimal {
public:
 Frog(string n, int a): landAnimal(n,a), waterAnimal(n,a), Animal(n,a) {}

 void makeSound() const override {
  cout << "Frog " << name << " makes sound" << endl;
 }

 void walk() const override {
  cout << "Frog " << name << " walks near lake" << endl;
 }

 void swim() const override {
  cout << "Frog " << name << " is swimming" << endl;
 }
};

int main() {
 vector<Animal*> zoo;

 zoo.push_back(new Lion("Alex", 27));
 zoo.push_back(new Dolphin("Amnyam", 52));
 zoo.push_back(new Frog("Crazy", 2));
 
 for (Animal* animal : zoo) {
  animal->makeSound();
 }

 // Clean up memory
 for (Animal* animal : zoo) {
  delete animal;
 }
}
