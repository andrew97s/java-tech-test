package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Clothing_tag_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clothing_tag();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Clothing_tag()
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

           int result=0;
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
          
           /*设置标签高度、间隙及偏移 */
           result_printing = PrintLab.PTK_SetLabelHeight(18 * mm, 24, 0, false); 
           if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

           /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
           result_printing = PrintLab.PTK_SetLabelWidth(71 * mm);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

           /* 打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(21 + offset_X), (int)(827 + offset_Y), 0, "E30", 3, 9, 81, 'N', "694773135006");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /* 打印条码下方文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(21 + offset_X), (int)(827 +81+4+ offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "品牌：博思得");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(165 + offset_X), (int)(10 + offset_Y), 42, 0, "Arial", 1, 400, false, false, false, "XXXX银行      档案资产管理"); //打印文字
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印矩形*/
           result_printing = PrintLab.PTK_DrawRectangle((int)(23 + offset_X), (int)(5 + offset_Y), 4, 817, 204); 
           if (result_printing != 0) { Printers.showError("PTK_DrawRectangle", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(24 + offset_X), (int)(51 + offset_Y), 791, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(24 + offset_X), (int)(87 + offset_Y), 791, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }
           
           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(24 + offset_X), (int)(126 + offset_Y), 791, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }


           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(24 + offset_X), (int)(163 + offset_Y), 541, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(41 + offset_X), (int)(56 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "储存编号");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(199 + offset_X), (int)(59 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "02200800201512250185");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(28 + offset_X), (int)(94 + offset_Y), 25, 0, "黑体", 1, 400, false, false, false, "档案封签编号");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(45 + offset_X), (int)(132 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "档案所属");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(45 + offset_X), (int)(168 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "机构名称");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(503 + offset_X), (int)(60 + offset_Y), 25, 0, "黑体", 1, 400, false, false, false, "档案类型");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(515 + offset_X), (int)(88 + offset_Y), 4, 39);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(501 + offset_X), (int)(51 + offset_Y), 4, 38);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(217 + offset_X), (int)(97 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "A01252951");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(408 + offset_X), (int)(96 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "客户号");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(597 + offset_X), (int)(98 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "2000403916");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(202 + offset_X), (int)(133 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "广东省深圳市XXXX有限公司");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(202 + offset_X), (int)(170 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "广东省深圳市分行");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }
           
           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(624 + offset_X), (int)(57 + offset_Y), 29, 0, "黑体", 1, 400, false, false, false, "房产抵押合同");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(609 + offset_X), (int)(53 + offset_Y), 4, 36);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(188 + offset_X), (int)(50 + offset_Y), 4, 153);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(563 + offset_X), (int)(129 + offset_Y), 4, 75);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(358 + offset_X), (int)(89 + offset_Y), 4, 38);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /* 打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(602 + offset_X), (int)(133 + offset_Y), 0, "1", 1, 3, 39, 'N', "A01252951");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(594 + offset_X), (int)(172 + offset_Y), 25, 0, "Arial", 1, 400, false, false, false, "A01252951");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
           result_printing = PrintLab.PTK_PrintLabel(1, 1); 
           if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          

           /*断开打印机连接*/
           PrintLab.PTK_CloseConnect();


           System.err.println("执行成功！");
           
       }
	  

}
