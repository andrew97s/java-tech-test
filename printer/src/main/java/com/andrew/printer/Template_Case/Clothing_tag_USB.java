package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Clothing_tag_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clothing_tag();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Clothing_tag()
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
          if (!PrinterStatus.equals("0"))
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
          result_printing = PrintLab.PTK_SetLabelHeight(80 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(40 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印条码*/
          result_printing = PrintLab.PTK_DrawBarcode((int)(46 + offset_X), (int)(712 + offset_Y), 0, "1A", 3, 9, 94, 'N', "T6225560");
          if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(162 + offset_X), (int)(818 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "T6225560"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(164 + offset_X), (int)(40 + offset_Y), 48, 0, "黑体", 1, 800, false, false, false, "合格证"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(47 + offset_X), (int)(139 + offset_Y), 42, 0, "黑体", 1, 800, false, false, false, "货号："); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(174 + offset_X), (int)(121 + offset_Y), 63, 0, "黑体", 1, 800, false, false, false, "T6225560"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(50 + offset_X), (int)(204 + offset_Y),42,0, "黑体", 1,800,false,false,false, "品名："); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(53 + offset_X), (int)(279 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "颜色："); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(50 + offset_X), (int)(346 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "规格："); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(50 + offset_X), (int)(407 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "等级："); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(58 + offset_X), (int)(461 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "质检员：  01"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(48 + offset_X), (int)(512 + offset_Y), 33, 0, " 黑体", 1, 800, false, false, false, "安全类别：    B类"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(49 + offset_X), (int)(564 + offset_Y), 33, 21, "黑体", 1, 400, false, false, false, "执行标准：FZ/T73008-2002"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(167 + offset_X), (int)(210 + offset_Y), 25, 0, "黑体", 1, 400, false, false, false,  "女装圆领T恤蕾丝花边针"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(167 + offset_X), (int)(235 + offset_Y), 25, 0, "黑体", 1, 800, false, false, false, "织修身连体裙"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(206 + offset_X), (int)(277 + offset_Y), 54, 0, "黑体", 1, 800, false, false, false, "红色"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(195 + offset_X), (int)(340 + offset_Y), 33, 0, "黑体", 1, 800, false, false, false, "110/52"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(47 + offset_X), (int)(608 + offset_Y), 25, 0, "黑体", 1, 400, false, false, false, "羊毛拉架面料  65%棉  30%"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(47 + offset_X), (int)(633 + offset_Y), 25, 0, "黑体", 1, 800, false, false, false, "聚酯纤维  5%氨纶"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(206 + offset_X), (int)(402 + offset_Y), 25, 0, "黑体", 1, 800, false, false, false, "合格品"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(39 + offset_X), (int)(666 +offset_Y),42,0, "黑体", 1,800,false,false,false, "JA12001532"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(54 + offset_X), (int)(862 + offset_Y), 46, 0, "黑体", 1, 800, false, false, false, "零售价：￥59.00"); 
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印本地*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(355 + offset_X), (int)(292 + offset_Y), "hot200", ".\\logo\\hot200.bmp", 0, 82, 49, 0);
          if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

          //*打印本地*/
          result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(347 + offset_X), (int)(409 + offset_Y), "DoNotWash", ".\\logo\\DoNotWash.bmp", 0.3f, 92, 74, 0);
          if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          /*断开打印机连接*/
          PrintLab.PTK_CloseUSBPort();


          System.err.println("执行成功！");
      }
	  

}
