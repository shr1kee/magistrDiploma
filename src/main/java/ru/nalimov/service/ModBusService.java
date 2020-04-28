package ru.nalimov.service;

import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import jssc.SerialPortList;
import org.springframework.stereotype.Service;

@Service
public class ModBusService {

    public void send() {

    }

    public void connect(){
        SerialParameters sp = new SerialParameters();
        String[] dev_list = SerialPortList.getPortNames();
        for (int i = 0; i < dev_list.length; i++){
            System.out.println(dev_list[i]);
        }
    }
}
