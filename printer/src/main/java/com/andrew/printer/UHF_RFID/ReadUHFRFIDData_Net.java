package com.andrew.printer.UHF_RFID;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class ReadUHFRFIDData_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadUHFRFIDData();
	}

	static CDFPSKDll PrintLab = CDFPSKDll.Instance;

	private static void ReadUHFRFIDData() {

		/* 设置打印机IP地址 */
		/*
		 * 打印之前先确保打印机已配置到你所处的局域网内，可用ping命令检测打印机是否已经在局域网内
		 * 打印机IP地址可以打印自检页查看，也可以在Utility或者液晶屏中查看 配置打印机的IP地址和端口号：可在打印机液晶屏或者Utility中配置
		 * 端口一般不用配置，用默认值9100就好
		 */

		String IPAddress = Printers.IPAddress;
		int port = Printers.NetPort;
		int result = 0;
		String message = "";

		/* 连接打印机 */
		result = PrintLab.PTK_Connect_Timer(IPAddress, port, 2);
		if (result != 0) {
			System.err.println(Printers.getErrorInfo(result));
			return;
		}

		/* 函数返回值 */
		int result_printing = 0;
		int[] status = new int[4];
		byte[] RFIDDataByte = new byte[1024];
		String RFIDData = "";
		String PrinterStatus = "";

		/* 获取打印机状态 */
		int[] status_Net = new int[4];
		result_printing = PrintLab.PTK_FeedBack();
		if (result_printing != 0) {
			Printers.showError("PTK_FeedBack", result_printing);
			return;
		}

		result_printing = PrintLab.PTK_ReadData(status_Net, 4);
		if (result_printing != 0) {
			Printers.showError("PTK_ReadData", result_printing);
			return;
		}

		PrinterStatus = status_Net[0] + "";

		if (!PrinterStatus.equals("0")) {
			PrintLab.PTK_CloseConnect();
			System.err.println("打印机未就绪，当前错误报告：" + Printers.Status_Explain(PrinterStatus));

			return;
		}

		/* 清空缓存 */
		result_printing = PrintLab.PTK_ClearBuffer();
		if (result_printing != 0) {
			Printers.showError("PTK_ClearBuffer", result_printing);
			return;
		}

		/* 读取UHF RFID 数据， 具体参数请查看API文档 */
		result_printing = PrintLab.PTK_ReadRFIDLabelData(2, 0, 1, RFIDDataByte, 1024);
		if (result_printing != 0) {
			int reee = PrintLab.PTK_GetLastError();
			Printers.showError("PTK_ReadRFIDLabelData:" + reee, result_printing);
			return;
		}

		RFIDData =new String(RFIDDataByte).trim();

		/* 断开打印机连接 */
		PrintLab.PTK_CloseConnect();

		System.err.println("读取RFID数据成功：" + RFIDData);

	}

}
