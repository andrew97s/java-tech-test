package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class NameplateLabelWithForm_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NameplateLabelWithForm();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void NameplateLabelWithForm()
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

          result_printing = PrintLab.PTK_ReadData(status_Net, 4);
          if (result_printing != 0) { Printers.showError("PTK_ReadData", result_printing); return; }

          PrinterStatus = status_Net[0] + "";

           if (!PrinterStatus.equals("0") && !PrinterStatus.equals("4"))
          {
              PrintLab.PTK_CloseConnect();
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
          result_printing = PrintLab.PTK_SetLabelHeight(30 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(90 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 20), (int)(offset_Y + 96), 1026, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 20), (int)(offset_Y + 170), 1025, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 531), (int)(offset_Y + 98), 4, 216);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 22), (int)(offset_Y + 242), 1026, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 21), (int)(offset_Y + 312), 1026, 4);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 244), (int)(offset_Y + 171), 4, 143);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 1044), (int)(offset_Y + 98), 4, 216);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 18), (int)(offset_Y + 98), 4, 217);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 353), (int)(offset_Y + 12), 75, 0, "方正舒体", 1, 800, false, false, false, "DONGSHAN");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 574), (int)(offset_Y + 191), 33, 0, "Arial", 1, 800, false, false, false, "ISO9001-2019");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 32), (int)(offset_Y + 187), 33, 0, "Arial", 1, 800, false, false, false, "Pn：1.8kW");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 298), (int)(offset_Y + 184), 33, 0, "Arial", 1, 800, false, false, false, "Tn：6N·m");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 572), (int)(offset_Y + 115), 44, 0, "Arial", 1, 800, false, false, false, "AC SERVO MOTOR");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 32), (int)(offset_Y + 257), 33, 0, "Arial", 1, 800, false, false, false, "Un：220V");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 306), (int)(offset_Y + 254), 33, 0, "Arial", 1, 800, false, false, false, "In：7A");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 575), (int)(offset_Y + 256), 33, 0, "Arial", 1, 800, false, false, false, "S/N:W112012161251");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 29), (int)(offset_Y + 115), 42, 0, "Arial", 1, 800, false, false, false, "TYPE：110ST-M04030D0");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }


          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

         

          /*断开打印机连接*/
          PrintLab.PTK_CloseConnect();


          System.err.println("执行成功！");
      }
	  

}
