package com.andrew.printer.HF_RFID;


import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class ReadHFRFIDData_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadHFRFIDData();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	  
	  private static void ReadHFRFIDData() {

          /*设置打印机IP地址*/
    /*打印之前先确保打印机已配置到你所处的局域网内，可用ping命令检测打印机是否已经在局域网内
     *打印机IP地址可以打印自检页查看，也可以在Utility或者液晶屏中查看
     * 配置打印机的IP地址和端口号：可在打印机液晶屏或者Utility中配置
     * 端口一般不用配置，用默认值9100就好
     */
  
    String IPAddress = Printers.IPAddress;
    int port = Printers.NetPort;
	  


    int result = 0;
    String message = "";
    byte[] data = new byte[1024];
    byte[] status = new byte[10];

    String RFIDData = "";
    String PrinterStatus = "";
    /*函数返回值*/
    int result_printing = 0;

    /*连接打印机*/
    result = PrintLab.PTK_Connect_Timer(IPAddress,port,2);
   if (result != 0) {
	   System.err.println( Printers.getErrorInfo(result));
	   return;
   }

    /*获取打印机状态*/
    int[] status_Net = new int[4];
    result_printing = PrintLab.PTK_FeedBack();
    if (result_printing != 0) { Printers.showError("PTK_FeedBack", result_printing); return; }

    result_printing = PrintLab.PTK_ReadData(status_Net, 4);
    if (result_printing != 0) { Printers.showError("PTK_ReadData", result_printing); return; }

    PrinterStatus = status_Net[0] + "";

    if (!PrinterStatus.equals("0"))
    {
        PrintLab.PTK_CloseConnect();
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
    PrintLab.PTK_CloseConnect();

    // Console.WriteLine("RFIDData:" + RFIDData + "\r\n");
    System.err.println("读取高频RFID数据成功：" + RFIDData);


      }

}
