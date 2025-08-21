package com.andrew.printer.HF_RFID;


import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class ReadHFRFIDData_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadHFRFIDData();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	  
	  private static void ReadHFRFIDData() {
          int result = 0;
          byte[] data = new byte[1024];
          int[] status_USB = new int[4];
          String RFIDData = "";
          String PrinterStatus = "";
          /*函数返回值*/
          int result_printing = 0;

          /*连接打印机*/
          result = PrintLab.PTK_OpenUSBPort(255);
         if (result != 0) {
        	 System.err.println( Printers.getErrorInfo(result));
        	return;
         }

          /*获取打印机状态*/
          result_printing = PrintLab.PTK_ErrorReport_USBInterrupt(status_USB);
          if (result_printing != 0) { Printers.showError("PTK_ErrorReport_USBInterrupt", result_printing); return; }
          PrinterStatus = status_USB[0] + "";


           if (!PrinterStatus.equals("0") && !PrinterStatus.equals("4"))
          {
              PrintLab.PTK_CloseUSBPort();
              System.err.println("打印机未就绪，当前错误报告：" + Printers.Status_Explain(PrinterStatus));

              return;
          }

          /*清空缓存*/
          result_printing = PrintLab.PTK_ClearBuffer();
          if (result_printing != 0) { Printers.showError("PTK_ClearBuffer", result_printing); return; }

          /*读取高频RFID 块数据*/
          //result_printing = PrintLab.PTK_ReadHFLabelData(1, 5, 'N', data, 256); //读取高频RFID块的数据
          /*读取高频RFID UID*/
          result_printing = PrintLab.PTK_ReadHFLabeUID('N', data, 256);
          if (result_printing != 0) { Printers.showError("PTK_ReadHFLabelData", result_printing); return; }
          RFIDData = new String(data).trim();

          /*断开打印机连接*/
          PrintLab.PTK_CloseUSBPort();
        
          System.err.println("读取高频RFID数据成功：" + RFIDData);
      }

}
