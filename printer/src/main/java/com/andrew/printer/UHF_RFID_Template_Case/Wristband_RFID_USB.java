package com.andrew.printer.UHF_RFID_Template_Case;
import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Wristband_RFID_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Wristband_RFID();
	}
	
	 static CDFPSKDll  PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Wristband_RFID()
      {
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

          /*设置标签高度、间隙及偏移 */
          result_printing = PrintLab.PTK_SetLabelHeight(295 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(32 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印一维码*/
          result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 252), (int)(offset_Y + 2302), 1, "1", 4, 12, 118, 'N', "130673");
          if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

          /*打印二维码*/
          result_printing = PrintLab.PTK_DrawBar2D_QR((int)(offset_X + 98), (int)(offset_Y + 3015), 0, 0, 0, 6, 4, 1, 8, "李爱华  男  58岁神经科： 16  床住院号：  130673");
          if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_QR", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 332), (int)(offset_Y + 2290), 57, 0,   "Arial", 2, 400, false, false, false, "广东省人民医院");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 269), (int)(offset_Y + 2598), 42, 0,   "Arial", 2, 400, false, false, false, "李爱华  男  58岁");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 194), (int)(offset_Y + 2598), 42, 0,   "Arial", 2, 400, false, false, false, "神经科： 16  床");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 119), (int)(offset_Y + 2598), 42, 0,   "Arial", 2, 400, false, false, false, "住院号：  130673");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*写RFID数据 具体参数请查看文档 写数据之前确保RFID标签已经校准成功*/
          //result_printing = PrintLab.PTK_RWRFIDLabel(1, 0, 0, 3, 1, "130673");
          if (result_printing != 0) { Printers.showError("PTK_RWRFIDLabel", result_printing); return; }

          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          /*断开打印机连接*/
          PrintLab.PTK_CloseUSBPort();


          System.err.println("执行成功！");
      }
	  

}
