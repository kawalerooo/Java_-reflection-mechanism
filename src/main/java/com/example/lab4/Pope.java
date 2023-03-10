package com.example.lab4;

public class Pope {
    private String name = "Jakub";
    private String surname = "Kawalerski";
    private int address = 2001;
    private int number = 2001;
    private String info = "PL";

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAddress() {
        return address;
    }

    public int getNumber() {return number;}

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Pope(String name, String surname, int address, int number) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.number = number;
    }

    public Pope() {
    }
}



