package com.andrew.printer.Other;


import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class GetPrinterStatus_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getPrinterStatus();
	}

	static CDFPSKDll PrintLab = CDFPSKDll.Instance;

	public static void getPrinterStatus() {
		int result = 0;
		String message = "";
		byte[] status = new byte[10];
		int[] status_USB = new int[4];
		String PrinterStatus = "";
		/* 函数返回值 */
		int result_printing = 0;

		/* 连接打印机 */
		result = PrintLab.PTK_OpenUSBPort(255);
		if (result != 0) {
			System.err.println(Printers.getErrorInfo(result));
			return;
		}

		/* 获取打印机状态 */
		result_printing = PrintLab.PTK_ErrorReport_USBInterrupt(status_USB);
		if (result_printing != 0) {
			Printers.showError("PTK_ErrorReport_USBInterrupt", result_printing);
			return;
		}
		PrinterStatus = status_USB[0] + "";

		/* 断开打印机连接 */
		PrintLab.PTK_CloseUSBPort();

		// Console.WriteLine("RFIDData:" + RFIDData + "\r\n");
		System.err.println("当前打印机状态：" + Printers.Status_Explain(PrinterStatus));
	}
}
