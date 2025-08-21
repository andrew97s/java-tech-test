package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class WashLabel_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WashLabel();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void WashLabel()
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

           result_printing = PrintLab.PTK_ReadData(status_Net,4);
           if (result_printing != 0) { Printers.showError("PTK_ReadData", result_printing); return; }
        
           PrinterStatus = status_Net[0]+"";
          
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
          
           /*设置标签高度、间隙及偏移 打印洗水唛打印机不需要校准标签，标签间隙设置为0*/
           result_printing = PrintLab.PTK_SetLabelHeight(90 * mm, 0, 0, false); 
           if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

           /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
           result_printing = PrintLab.PTK_SetLabelWidth(30 * mm);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

           /* 打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(21 + offset_X), (int)(827 + offset_Y), 0, "E30", 3, 9, 81, 'N', "694773135006");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /* 打印条码下方文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(21 + offset_X), (int)(827 + 81 + 4 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "694773135006");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(102 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "品牌：博思得"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(137 + offset_Y),  33, 0, "Arial", 1, 400, false, false, false, "品名：短袖衬衣"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(175 + offset_Y),  33, 0, "Arial", 1, 400, false, false, false, "等级：一等品"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(214 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "检验员：008"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(248 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "执行标注：GB/T2660-2008"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(283 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "货号：X112920392-B3"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(317 + offset_Y),  33, 0, "Arial", 1, 400, false, false, false, "号型：180/96A(52)"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(356 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "颜色：黑色"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(391 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "面料成份：100%棉"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(425 + offset_Y),  33, 0, "Arial", 1, 400, false, false, false, "安全类别：B类(直接接触皮肤)"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(19 + offset_X), (int)(464 + offset_Y),  29, 0, "Arial", 1, 400, false, false, false, "洗涤说明："); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(503 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "最高洗涤温度30℃"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(533 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "温和程序"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(591 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "不可漂白"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(85 + offset_X), (int)(939 + offset_Y), 44, 0, "Arial", 1, 400, false, false, false, "产地：广州"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(774 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "常规干洗"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(699 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "烫斗底板"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(651 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "悬挂晾干"); 
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(119 + offset_X), (int)(730 + offset_Y), 29, 0, "Arial", 1, 400, false, false, false, "最高温度150℃");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /* 打印本地图片*/
           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(32 + offset_X), (int)(510 + offset_Y), "WT30", "./logo/WT30.bmp", 0.2f, 82, 49, 0); //打印图片,此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(32 + offset_X), (int)(588 + offset_Y), "DoNotBleach", ".\\logo\\DoNotBleach.bmp", 0.17f, 92, 74, 0); //打印图片，此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           /* 打印本地图片*/
           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(42 + offset_X), (int)(646 + offset_Y), "HangToDry", ".\\logo\\HangToDry.bmp", 0.15f, 82, 49, 0); //打印图片,此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           /* 打印本地图片*/
           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(37 + offset_X), (int)(710 + offset_Y), "hot150", ".\\logo\\hot150.bmp", 0.19f, 92, 74, 0); //打印图片，此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           /* 打印本地图片*/
           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(42 + offset_X), (int)(759 + offset_Y), "DryCleaning", ".\\logo\\DryCleaning.bmp", 0.14f, 92, 74, 0); //打印图片，此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           /* 打印本地图片*/
           result_printing = PrintLab.PTK_AnyGraphicsPrint((int)(16 + offset_X), (int)(33 + offset_Y), "logo", ".\\logo\\logo.jpg", 0.8f, 92, 74, 0); //打印图片，此处为了方便演示图片内置
           if (result_printing != 0) { Printers.showError("PTK_AnyGraphicsPrint", result_printing); return; }

           /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
           result_printing = PrintLab.PTK_PrintLabel(1, 1); 
           if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          

           /*断开打印机连接*/
           PrintLab.PTK_CloseConnect();


           System.err.println("执行成功！");
       }
	  

}
