package com.andrew.printer.GeneralLabel;


import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class PrintGraphics_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("jna.encoding", "UTF-8");
		PrintGraphics();
	}
	
  static CDFPSKDll  PrintLab= CDFPSKDll.Instance;
  private static void PrintGraphics() {
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
       result = PrintLab.PTK_OpenUSBPort(255);
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
       int[] status_USB = new int[4];
       String PrinterStatus = "";
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

       /* 打印图片*/
       /*/打印网络图片*/
       result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(200 + offset_X), (int)(20 + offset_Y), "picture", "https://www.postek.com.cn/print/logo.png", 1, 0, 0, 0);
       /*打印本地图片*/
       //result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(20 + offset_X), (int)(20 + offset_Y),"picture","D:/logo.jpg",1,0,0,0); 
       if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

       /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
       result_printing = PrintLab.PTK_PrintLabel(1, 1);
       if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

       /*断开打印机连接*/
       PrintLab.PTK_CloseUSBPort();


       System.err.println("执行成功！");
  }
}
