package com.andrew.printer.UHF_RFID;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class ReadUHFRFIDData_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadUHFRFIDData();
	}

	static CDFPSKDll PrintLab = CDFPSKDll.Instance;

	private static void ReadUHFRFIDData() {
        int result = 0;
        String message = "";

        /*连接打印机*/
        result = PrintLab.PTK_OpenUSBPort(255);
       if (result != 0) {
      System.err.println( Printers.getErrorInfo(result));
      return;
  }

        /*函数返回值*/
        int result_printing = 0;
        int[] status = new int[4];
        byte[] RFIDDataByte = new byte[1024];
        String RFIDData = "";
        String PrinterStatus = "";

        /*获取打印机状态*/
        result_printing = PrintLab.PTK_ErrorReport_USBInterrupt(status);
        if (result_printing != 0) { Printers.showError("PTK_ErrorReport_USBInterrupt", result_printing); return; }
        PrinterStatus = status[0] + "";


        if (!PrinterStatus.equals("0")&& !PrinterStatus.equals("4"))
        {
            PrintLab.PTK_CloseUSBPort();
            System.err.println("打印机未就绪，当前错误报告：" + Printers.Status_Explain(PrinterStatus));
            
            return;
        }


        /*清空缓存*/
        result_printing = PrintLab.PTK_ClearBuffer();
        if (result_printing != 0) { Printers.showError("PTK_ClearBuffer", result_printing); return; }

        /*读取UHF RFID 数据， 具体参数请查看API文档 */
        result_printing = PrintLab.PTK_ReadRFIDLabelData(2, 0, 1, RFIDDataByte, 1024);
        if (result_printing != 0) {
            int reee = PrintLab.PTK_GetLastError();
            String aa = PrintLab.PTK_GetLastError() + "";
            Printers.showError("PTK_ReadRFIDLabelData:"+ reee+ "PTK_GetLastError:"+ aa, result_printing); return; }

        RFIDData = new String(RFIDDataByte).trim();

        /*断开打印机连接*/
        PrintLab.PTK_CloseUSBPort();


        System.err.println("读取RFID数据成功："+ RFIDData);

    }

}
