package com.andrew.printer.UHF_RFID_Template_General;
import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class EncodeRFID_Read_Print_job_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EncodeRFID_Read_Print_job();
	}
	
	 static CDFPSKDll  PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void EncodeRFID_Read_Print_job()
      {
          /*设置打印机IP地址*/
     /*打印之前先确保打印机已配置到你所处的局域网内，可用ping命令检测打印机是否已经在局域网内
      *打印机IP地址可以打印自检页查看，也可以在Utility或者液晶屏中查看
      * 配置打印机的IP地址和端口号：可在打印机液晶屏或者Utility中配置
      * 端口一般不用配置，用默认值9100就好
      */
   
     String IPAddress = Printers.IPAddress;
     int port = Printers.NetPort;
	  
     /*
     *mm:点，毫米转为点，由打印机分辨率决定
     * 打印机分辨DPI=203时， mm=8
     * 打印机分辨DPI=300时， mm=12
     * 打印机分辨DPI=600时， mm=24
     */
     int mm = 12;

           int result = 0;
           String message = "";

              /*连接打印机*/
         result = PrintLab.PTK_Connect_Timer(IPAddress,port,2);
        if (result != 0) {
       	 System.err.println( Printers.getErrorInfo(result));
       	return;
        }

           byte[] data = new byte[10240];
           byte[] status = new byte[48];
           String RFIDData = "";
           String PrinterStatus = "";
           /*函数返回值*/
           int result_printing = 0;

           /*横坐标偏移,设置标签整体横向移动位置*/
           int offset_X = 0;

           /*纵坐标偏移，设置标签整体纵向移动位置*/
           int offset_Y = 0;



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


           for (int i = 0; i < 1; i++) { 
           
           /*清空缓存*/
           result_printing = PrintLab.PTK_ClearBuffer();
           if (result_printing != 0) { Printers.showError("PTK_ClearBuffer", result_printing); return; }

           /*设置打印黑度 取值范围 0-20*/
           result_printing = PrintLab.PTK_SetDarkness(10);
           if (result_printing != 0) { Printers.showError("PTK_SetDarkness", result_printing); return; }

           /*设置打印速度*/
           result_printing = PrintLab.PTK_SetPrintSpeed(4);
           if (result_printing != 0) { Printers.showError("PTK_SetPrintSpeed", result_printing); return; }

           /*设置打印方向*/
           result_printing = PrintLab.PTK_SetDirection('B');
           if (result_printing != 0) { Printers.showError("PTK_SetDirection", result_printing); return; }

           /*设置标签高度、间隙及偏移*/
           result_printing = PrintLab.PTK_SetLabelHeight(19 * mm, 24, 0, false);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

           /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
           result_printing = PrintLab.PTK_SetLabelWidth(80 * mm);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

           /*UHF RFID打印设置具体参数请查看API文档*/
           result_printing = PrintLab.PTK_SetRFID(0, 0, 0, 0, 0);
           if (result_printing != 0) { Printers.showError("PTK_SetRFID", result_printing); return; }

           /*写RFID数据 具体参数请查看API文档  *^-^*写数据之前确保RFID标签已经校准成功*/
           result_printing = PrintLab.PTK_RWRFIDLabel(1, 0, 0, 16, 1, "4C14F7E75CF8E73C32C30C30C30C31D2");
           if (result_printing != 0) { Printers.showError("PTK_RWRFIDLabel", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(93 + offset_X), (int)(65 + offset_Y), 53, 0, "Arial", 1, 400, false, false, false, "SAS795389302000000014" + i);
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印QR码*/
           result_printing = PrintLab.PTK_DrawBar2D_QR((int)(23 + offset_X), (int)(61 + offset_Y), 0, 0, 0, 3, 4, 1, 8, "SAS795389302000000014" + i);
           if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_QR", result_printing); return; }


           /**打印并返回RFID数据和打印机状态 此函数仅支 持 V 1 0 0 以上固件
           result_printing = PrintLab.PTK_ReadRFIDSetting(2, 0, 5, "00000000") ;
           if (result_printing != 0) { Printers.showError("PTK_ReadRFIDSetting", result_printing); return; }
           result_printing = PrintLab.PTK_PrintAndCallback(data, status);
           if (result_printing != 0) { Printers.showError("PTK_PrintAndCallback", result_printing); return; }
           */ 

           /*打印并返回RFID数据和打印机状态*/
           result_printing = PrintLab.PTK_RFIDEndPrintLabelFeedBack(2, data, 10240, status, 48);
           if (result_printing != 0) { Printers.showError("PTK_RFIDEndPrintLabelFeedBack", result_printing); return; }

           RFIDData = new String(data).trim();
           PrinterStatus = new String(status).trim();
           System.out.println(RFIDData + PrinterStatus);

           }
          

           /*断开打印机连接*/
           PrintLab.PTK_CloseConnect();
           System.err.println("执行成功！\r\n" + "RFIDData:" + RFIDData + "\r\nPrinterStatus:" + PrinterStatus);

       }
	  

}
