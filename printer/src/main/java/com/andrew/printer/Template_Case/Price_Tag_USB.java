package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Price_Tag_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Price_Tag();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Price_Tag()
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
          result_printing = PrintLab.PTK_SetLabelHeight(130 * mm, 24, 0, false);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

          /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
          result_printing = PrintLab.PTK_SetLabelWidth(90 * mm);
          if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 0), (int)(offset_Y + 496), 1063, 12);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 647), (int)(offset_Y + 499), 12, 403);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 0), (int)(offset_Y + 899), 1063, 12);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印线条*/
          result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 0), (int)(offset_Y + 1226), 1063, 12);
          if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

          /*打印矩形*/
          result_printing = PrintLab.PTK_DrawRectangle((int)(offset_X + 653), (int)(offset_Y + 503), 4, 1063, 623); 
          if (result_printing != 0) { Printers.showError("PTK_DrawRectangle", result_printing); return; }

          /*打印一维码*/
          result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 137), (int)(offset_Y + 939), 0, "1", 5, 15, 188, 'N', "23883106000260001840");
          if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

          /*打印QR码*/
          result_printing = PrintLab.PTK_DrawBar2D_QR((int)(offset_X + 804), (int)(offset_Y + 168), 0, 0, 0, 7, 0, 0, 8, "产地：云南 销售商：深圳市博思得科技发展有限公司");
          if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_QR", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 326), (int)(offset_Y + 17), 58, 0, "Arial", 1, 800, false, false, false, "特选上海青");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 95), (int)(offset_Y + 90), 58, 0, "Arial", 1, 400, false, false, false, "SELECTED  GREEN  BRASSICA");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 10), (int)(offset_Y + 216), 58, 0, "Arial", 1, 800, false, false, false, "上海青常温");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 8), (int)(offset_Y + 308), 55, 0, "Arial", 1, 400, false, false, false, "CEYLON SPINACH AMBIENT");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 414), 58, 0, "Arial", 1, 800, false, false, false, "生产日期/Production date:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 721), (int)(offset_Y + 411), 57, 0, "Arial", 1, 400, false, false, false, "2012-03-23");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 518), 38, 0, "Arial", 1, 800, false, false, false, "此日期或之前食用");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 402), (int)(offset_Y + 519), 38, 0, "Arial", 1, 400, false, false, false, "2012-03-23");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 577), 38, 0, "Arial", 1, 800, false, false, false, "Use by");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 633), 38, 0, "Arial", 1, 800, false, false, false, "每斤单价");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 681), 38, 0, "Arial", 1, 800, false, false, false, "￥/");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 109), (int)(offset_Y + 678), 38, 0, "Arial", 1, 400, false, false, false, "500g");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 734), 38, 0, "Arial", 1, 800, false, false, false, "净重/数量");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 306), (int)(offset_Y + 679), 38, 0, "Arial", 1, 400, false, false, false, "6.98");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 284), (int)(offset_Y + 735), 38, 0, "Arial", 1, 400, false, false, false, "0.184");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 785), 38, 0, "Arial", 1, 800, false, false, false, "Net WT/Quantity");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

         // result_printing = PrintLab.PTK_SetCharSets(8,'U',"001");
         // if (result_printing != 0) { Printers.showError("PTK_SetCharSets", result_printing); return; }

          /*调用打印机内置字体打印*/
          result_printing = PrintLab.PTK_DrawText((int)(offset_X + 655), (int)(offset_Y + 514), 0, '6', 2, 4, 'R', "零售价:Total Price(￥)");
          if (result_printing != 0) { Printers.showError("PTK_DrawText", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 742), (int)(offset_Y + 645), 146, 0, "Arial", 1, 800, false, false, false, "2.60");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 249), (int)(offset_Y + 1127), 50, 0,  "Arial", 1, 800, false, false, false, "23883106000260001840");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 1254), 33, 0, "Arial", 1, 800, false, false, false, "标价员：");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 153), (int)(offset_Y + 1254), 33, 0, "Arial", 1, 400, false, false, false, "李明明");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 503), (int)(offset_Y + 1251), 33, 0, "Arial", 1, 800, false, false, false, "产地：");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 685), (int)(offset_Y + 1247), 33, 0, "Arial", 1, 800, false, false, false, "云南");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 503), (int)(offset_Y + 1301), 33, 0, "Arial", 1, 800, false, false, false, "Origin:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 696), (int)(offset_Y + 1301), 33, 0, "Arial", 1, 800, false, false, false, "YN");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 1302), 33, 0, "Arial", 1, 800, false, false, false, "Pricing by:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 1345), 33, 0, "Arial", 1, 800, false, false, false, "销售商/Sell by:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 245), (int)(offset_Y + 1347), 33, 0, "Arial", 1, 800, false, false, false, "POSTEK");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 7), (int)(offset_Y + 1393), 33, 0,   "Arial", 1, 800, false, false, false, "电话/Tell:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 188), (int)(offset_Y + 1389), 33, 0,   "Arial", 1, 800, false, false, false, "0755-83240988");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 9), (int)(offset_Y + 1447), 33, 0,   "Arial", 1, 800, false, false, false, "地址/Address:");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印文字*/
          result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 237), (int)(offset_Y + 1446), 33, 0,   "Arial", 1, 400, false, false, false, "深圳市南山区侨香路4068号");
          if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

          /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
          result_printing = PrintLab.PTK_PrintLabel(1, 1);
          if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          /*断开打印机连接*/
          PrintLab.PTK_CloseUSBPort();


          System.err.println("执行成功！");
      }
	  

}
