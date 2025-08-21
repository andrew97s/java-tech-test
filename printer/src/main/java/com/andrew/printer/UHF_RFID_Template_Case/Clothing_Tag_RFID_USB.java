package com.andrew.printer.UHF_RFID_Template_Case;
import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Clothing_Tag_RFID_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clothing_Tag_RFID();
	}
	
	 static CDFPSKDll  PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Clothing_Tag_RFID()
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
          result_printing = PrintLab.PTK_SetLabelHeight(85 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(40 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印本地图片*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(offset_X + 44), (int)(offset_Y + 670), "DoNotBleach", ".\\logo\\DoNotBleach.bmp", 0.16f, 92, 74, 0);
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印本地图片*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(offset_X + 44), (int)(offset_Y + 730), "DFITS", ".\\logo\\DFITS.bmp", 0.16f, 92, 74, 0);
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印本地图片*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(offset_X + 44), (int)(offset_Y + 799), "VW30", ".\\logo\\VW30.bmp", 0.18f, 92, 74, 0);
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 168), (int)(offset_Y + 25), 44, 0,   "Arial", 1, 400, false, false, false, "合格证");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 39), (int)(offset_Y + 81), 38, 0,   "Arial", 1, 400, false, false, false, "品名：女装T");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 39), (int)(offset_Y + 133), 38, 0,   "Arial", 1, 400, false, false, false, "颜色：淡蓝");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 36), (int)(offset_Y + 187), 38, 0,   "Arial", 1, 400, false, false, false, "号型：S");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 39), (int)(offset_Y + 241), 38, 0,   "Arial", 1, 400, false, false, false, "成份：100%棉");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 38), (int)(offset_Y + 296), 38, 0,   "Arial", 1, 400, false, false, false, "执行标准：");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 38), (int)(offset_Y + 334), 38, 0,   "Arial", 1, 400, false, false, false, "GB/T22849-2212");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 34), (int)(offset_Y + 536), 40, 0,   "Arial", 1, 400, false, false, false, "等级：一级  检验：01");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 35), (int)(offset_Y + 602), 38, 0,   "Arial", 1, 400, false, false, false, "洗涤方法：");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 107), (int)(offset_Y + 805), 38, 0,   "Arial", 1, 400, false, false, false, "30°C以下水洗");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 112), (int)(offset_Y + 668), 38, 0,   "Arial", 1, 400, false, false, false, "不可漂洗");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 114), (int)(offset_Y + 730), 38, 0,   "Arial", 1, 400, false, false, false, "阴凉处平摊晾干");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*写RFID数据 具体参数请查看文档 写数据之前确保RFID标签已经校准成功*/
          result_printing = PrintLab.PTK_RWRFIDLabel(1, 0, 0, 4, 1, "20160830");
          if (result_printing != 0) { Printers.showError("PTK_RWRFIDLabel", result_printing); return; }

          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          /*断开打印机连接*/
          PrintLab.PTK_CloseUSBPort();


          System.err.println("执行成功！");
      }
	  

}
