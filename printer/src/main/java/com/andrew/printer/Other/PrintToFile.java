package com.andrew.printer.Other;


import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class PrintToFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printToFile();
	}

	static CDFPSKDll PrintLab = CDFPSKDll.Instance;
	 private static void printToFile()
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

         /*创建一个文件，发送到打印 机的数据将被写入到该文件*/
         result = PrintLab.PTK_OpenTextPort("D:/printingData.txt");;
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
         result_printing = PrintLab.PTK_SetLabelHeight(30 * mm, 24, 0, false);
         if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

         /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
         result_printing = PrintLab.PTK_SetLabelWidth(50 * mm);
         if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

         /*打印本地图片*/
         //result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(20 + offset_X), (int)(20 + offset_Y),"picture","D;/POSTEK.png",1,0,0,0);
         //if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint",result_printing); return; }

         /*打印矩形*/
         result_printing = PrintLab.PTK_DrawRectangle((int)(18 + offset_X), (int)(12 + offset_Y), 4, 585, 343);
         if (result_printing != 0) { Printers.showError("PTK_DrawRectangle", result_printing); return; }

         /*打印一维码 此示例为Code128码*/
         result_printing = PrintLab.PTK_DrawBarcode((int)(30 + offset_X), (int)(99 + offset_Y), 0, "1", 2, 6, 40, 'N', "12345678");//打印一维码
         if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

         /* 打印条码下方文字*/
         result_printing = PrintLab.PTK_DrawText_TrueType((int)(30 + offset_X), (int)(99 + 40 + 4 + offset_Y), 2 * mm, 0, "Arial", 1, 400, false, false, false, "12345678");
         if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

         /* 打印文字*/
         //result_printing = PrintLab.PTK_DrawText_TrueType((int)(80 + offset_X), (int)(180 + offset_Y), 4 * mm, 0, "宋体", 1, 400, false, false, false, "博思得科技发展有限公司");
         //if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }


         /*打印一维码 此示例为UPC码*/
         result_printing = PrintLab.PTK_DrawBarcode((int)(213 + offset_X), (int)(98 + offset_Y), 0, "UE0", 2, 6, 47, 'N', "234567");
         if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

         /* 打印条码下方文字*/
         result_printing = PrintLab.PTK_DrawText_TrueType((int)(213 + offset_X), (int)(98 + 47 + 4 + offset_Y), 2 * mm, 0, "Arial", 1, 400, false, false, false, "12345678");
         if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

         /*打印二维码 此示例为QR码 付款码就是这个码*/
         result_printing = PrintLab.PTK_DrawBar2D_QR((int)(30 + offset_X), (int)(182 + offset_Y), 0, 0, 0, 3, 4, 1, 8, "设计精湛 ABCD 1234 ^-^ @@ && $$ ￥￥");
         if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_QR", result_printing); return; }

         /*打印二维码 此示例为datamatrix码*/
         result_printing = PrintLab.PTK_DrawBar2D_DATAMATRIX((int)(272 + offset_X), (int)(182 + offset_Y), 0, 0, 0, 5, "设计精湛 ABCD 1234 ^-^ @@ && $$ ￥￥");
         if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_DATAMATRIX", result_printing); return; }

         /*打印二维码 此示例为PDF417码*/
         result_printing = PrintLab.PTK_DrawBar2D_Pdf417((int)(25 + offset_X), (int)(288 + offset_Y), 240, 38, 3, 0, 2, 2, 10, 6, 0, 0, "设计精湛 ABCD 1234 ^-^ @@ && $$ ￥￥");
         if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_Pdf417", result_printing); return; }

         /*打印二维码 此示例为汉信码*/
         result_printing = PrintLab.PTK_DrawBar2D_HANXIN((int)(155 + offset_X), (int)(182 + offset_Y), 0, 0, 0, 3, 3, 0, 0, "设计精湛 ABCD 1234 ^-^ @@ && $$ ￥￥");
         if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_HANXIN", result_printing); return; }

         /*打印斜线*/
         result_printing = PrintLab.PTK_DrawDiagonal((int)(374 + offset_X), (int)(343 + offset_Y), 4, 478, 222);
         if (result_printing != 0) { Printers.showError("PTK_DrawDiagonal", result_printing); return; }

         /*打印斜线*/
         result_printing = PrintLab.PTK_DrawDiagonal((int)(478 + offset_X), (int)(222 + offset_Y), 4, 585, 343);
         if (result_printing != 0) { Printers.showError("PTK_DrawDiagonal", result_printing); return; }

         /*打印直线*/
         result_printing = PrintLab.PTK_DrawLineOr((int)(20 + offset_X), (int)(90 + offset_Y), 562, 4);
         if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

         /*打印直线*/
         result_printing = PrintLab.PTK_DrawLineOr((int)(374 + offset_X), (int)(90 + offset_Y), 4, 252);
         if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

         /* 打印文字*/
         result_printing = PrintLab.PTK_DrawText_TrueType((int)(112 + offset_X), (int)(21 + offset_Y), 28, 0, "华文行楷", 1, 400, false, false, false, "深圳市博思得科技发展有限公司");
         if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

         /* 打印文字*/
         result_printing = PrintLab.PTK_DrawText_TrueType((int)(125 + offset_X), (int)(53 + offset_Y), 23, 0, "Magneto", 1, 400, false, false, false, "Postek Electronics Co., Ltd.");
         if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

         /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
         result_printing = PrintLab.PTK_PrintLabel(1, 1);
         if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }


         /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
         result_printing = PrintLab.PTK_CloseTextPort();
         if (result_printing != 0) { Printers.showError("PTK_CloseTextPort", result_printing); return; }

     
     }
}
