package com.andrew.printer.GeneralLabel;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;


public class PrintBarcode_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("jna.encoding", "UTF-8");
		PrintText();
	}
	
  static CDFPSKDll PrintLab=CDFPSKDll.Instance;
 
  private static void PrintText() {

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
      

      int result=0;

      /*连接打印机*/
      result = PrintLab.PTK_Connect_Timer(IPAddress,port,5);
      if (result != 0) {
          System.err.println( Printers.getErrorInfo(result));
          return;
      }
      
      /*函数返回值*/
      int result_printing = 0;
      /*横坐标偏移,设置标签整体横向移动位置*/
      int offset_X = 0;
       
      /*纵坐标偏移，设置标签整体纵向移动位置*/
      int offset_Y = 0;


      /*获取打印机状态*/
      int[] status_Net = new int[4];
      String PrinterStatus = "";
      result_printing = PrintLab.PTK_FeedBack();
      if (result_printing != 0) { Printers.showError("PTK_FeedBack", result_printing); return; }

      result_printing = PrintLab.PTK_ReadData(status_Net,4);
      if (result_printing != 0) { Printers.showError("PTK_ReadData", result_printing); return; }
   
      PrinterStatus = status_Net[0]+"";
     
      if (!PrinterStatus.equals("0") && !PrinterStatus.equals("4"))
      {
          PrintLab.PTK_CloseConnect();
          System.err.println("打印机未就绪，当前错误报告：" +Printers.Status_Explain(PrinterStatus));

          return;
      }



      /*清空缓存*/
      result_printing = PrintLab.PTK_ClearBuffer();     
      if (result_printing != 0) { Printers.showError("PTK_ClearBuffer",result_printing); return; }

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
      result_printing = PrintLab.PTK_SetLabelHeight(75 * mm, 24, 0, false); 
      if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

      /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
      result_printing = PrintLab.PTK_SetLabelWidth(104 * mm);
      if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }
      
      /* 打印条码*/
      result_printing = PrintLab.PTK_DrawBarcode((int)(40 + offset_X), (int)(40 + offset_Y), 0, "1", 2, 2, 4 * mm, 'N', "123456789");
      if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

      /* 打印条码下方文字   20+4*mm+4为条码的Y坐标+条码高度+条码与文字间距*/
      result_printing = PrintLab.PTK_DrawText_TrueType((int)(40 + offset_X), (int)(40+4*mm+4+ offset_Y), 3 * mm, 0, "Arial", 1, 400, false, false, false, "123456789");
      if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }
     
    
      /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
      result_printing = PrintLab.PTK_PrintLabel(1, 1); 
      if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

      /*断开打印机连接*/
      PrintLab.PTK_CloseConnect();


      System.out.println("执行成功！");
}
  

}
