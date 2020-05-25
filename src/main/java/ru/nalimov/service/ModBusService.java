package ru.nalimov.service;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import jssc.SerialPortList;
import org.springframework.stereotype.Service;

@Service
public class ModBusService {

    private static ModbusMaster modbusMaster;

    public void send() throws ModbusNumberException, ModbusProtocolException, ModbusIOException {
        modbusMaster.writeSingleRegister(0, 0,0);
    }

    public int[] connect() throws SerialPortException, ModbusIOException, ModbusNumberException, ModbusProtocolException {
        SerialParameters sp = new SerialParameters();
        String[] dev_list = SerialPortList.getPortNames();
        if (dev_list.length == 0) {
            return new int[0];
        }
        sp.setDevice(dev_list[0]);
        modbusMaster = ModbusMasterFactory.createModbusMasterRTU(sp);
        modbusMaster.connect();
        int slaveId = 1;
        int offset = 0;
        int quantity = 100;
        int[] registerValues = modbusMaster.readHoldingRegisters(slaveId, offset, quantity);
        return registerValues;
    }
}
