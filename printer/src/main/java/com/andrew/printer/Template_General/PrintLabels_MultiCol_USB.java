package com.andrew.printer.Template_General;
import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class PrintLabels_MultiCol_USB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintLabels_MultiCol();
	}
	 static CDFPSKDll  PrintLab=CDFPSKDll.Instance;
	 
	 static String[] PrinterList_data;
     String printers="";

     
	  private static void PrintLabels_MultiCol()
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
	            /*标签有多少列*/
	            int column = 5;

	            /*打印份数*/
	            int printNum = 11;

	            /*单列标签的宽度 （每张小标签的宽度）*/
	            int width = 20 * mm;

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
	            result_printing = PrintLab.PTK_SetLabelHeight(30 * mm, 24, 0, false);
	            if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

	            /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
	            result_printing = PrintLab.PTK_SetLabelWidth(104 * mm);
	            if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }


	            for (int i = 1; i < printNum + 1; i++)
	            {
	                /*计算当前处于第几行*/
	            	int row = i / column;

	                /*用取余来判断当前处于第几列，由于i从1开始，所以当取余为0时恰好处于最后一列*/
	            	int col = i % column;

	                /*如果取余得到的行号为0则为下一行*/
	            	int row_cr = col == 0 ? row : row + 1;

	                /*当前处于的列数*/
	            	int col_cr = col == 0 ? column : col;

	                /* 打印文字*/
	                result_printing = PrintLab.PTK_DrawText_TrueType((int)((col_cr - 1) * width+80 + offset_X), (int)(160 + offset_Y), 2* mm, 0, "宋体", 1, 400, false, false, false, "第" + row_cr + "行" + "第" + col_cr + "列");
	                if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

	                /* 打印QR码*/
	                result_printing = PrintLab.PTK_DrawBar2D_QREx((int)((col_cr - 1) * width + 80 + offset_X), (int)(10 + offset_Y), 0, 5, 1, 0, 8, "ss", "博思得科技发展有限公司");
	                if (result_printing != 0) { Printers.showError("PTK_DrawBar2D_QREx", result_printing); return; }
	                System.out.println("-------打印第" + row_cr + "行，第" + col_cr + "列");

	                if (col == 0 || i == printNum)
	                {
	                    /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
	                    result_printing = PrintLab.PTK_PrintLabel(1, 1);
	                    if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }
	                }
	            }

	            /*断开打印机连接*/
	            PrintLab.PTK_CloseUSBPort();


	            System.err.println("执行成功！");

	     
	        }
	  


}
