package com.andrew.printer.Template_Case;

import com.andrew.printer.MainInterface.CDFPSKDll;
import com.andrew.printer.MainInterface.Printers;

public class Electronic_Product_Box_Label_Net {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Electronic_Product_Box_Label();
	}
	
	 static CDFPSKDll PrintLab=CDFPSKDll.Instance;
	 static String[] PrinterList_data;
     String printers="";
     
	  private static void Electronic_Product_Box_Label()
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
           result_printing = PrintLab.PTK_SetLabelHeight(102 * mm, 24, 0, false);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelHeight", result_printing); return; }

           /*设置标签宽度，一定要准确，否则会导致打印内容位置不准确*/
           result_printing = PrintLab.PTK_SetLabelWidth(106 * mm);
           if (result_printing != 0) { Printers.showError("PTK_SetLabelWidth", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 25), (int)(offset_Y + 3), 4, 1133);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 252), 1191, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 383), 1191, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 639), 1190, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 1027), 1190, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 530), 860, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

			/*打印线条*/
			result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 30), (int)(offset_Y + 759), 1187, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 943), 1193, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

			/*打印线条*/
			result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 1078), 1189, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 456), (int)(offset_Y + 945), 4, 134);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 672), (int)(offset_Y + 947), 4, 135);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

			/*打印线条*/
			result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 912), (int)(offset_Y + 947), 4, 134);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 2), 1190, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 508), (int)(offset_Y + 124), 4, 130);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 755), (int)(offset_Y + 120), 4, 134);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 26), (int)(offset_Y + 322), 859, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 454), 860, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 28), (int)(offset_Y + 589), 1190, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 886), (int)(offset_Y + 254), 4, 334);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 688), 1191, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 304), (int)(offset_Y + 2), 4, 382);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 417), (int)(offset_Y + 384), 4, 558);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 25), (int)(offset_Y + 852), 1192, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 236), (int)(offset_Y + 947), 4, 188);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 121), 1190, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 1216), (int)(offset_Y + 4), 4, 1133);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印线条*/
           result_printing = PrintLab.PTK_DrawLineOr((int)(offset_X + 27), (int)(offset_Y + 1136), 1191, 4);
           if (result_printing != 0) { Printers.showError("PTK_DrawLineOr", result_printing); return; }

           /*打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 574), (int)(offset_Y + 867), 0, "1",   2, 6, 40, 'N', "13E1028BTS0002649");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /*打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 649), (int)(offset_Y + 776), 0, "E30", 3, 9, 45, 'N', "693303722555");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /*打印条码下方文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 649), (int)(offset_Y + 776+45+2), 30, 0, "Arial", 1, 400, false, false, false, "693303722555");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印一维码*/
           result_printing = PrintLab.PTK_DrawBarcode((int)(offset_X + 638), (int)(offset_Y + 700), 0, "UA0", 3, 9, 40,  'N', "19028010145");
           if (result_printing != 0) { Printers.showError("PTK_DrawBarcode", result_printing); return; }

           /*打印条码下方文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 638), (int)(offset_Y + 700+40), 25, 0, "Arial", 1, 400, false, false, false, "19028010145");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 107), (int)(offset_Y + 149), 30, 0, "微软雅黑", 1, 400, false, false, false, "产品型号");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 69), (int)(offset_Y + 194), 30, 0, "微软雅黑", 1, 400, false, false, false, "Product Code");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 351), (int)(offset_Y + 175), 30, 0, "微软雅黑", 1, 400, false, false, false, "E1088BT");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 566), (int)(offset_Y + 149), 30, 0, "微软雅黑", 1, 400, false, false, false, "产品名称");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 535), (int)(offset_Y + 194), 30, 0, "微软雅黑", 1, 400, false, false, false, "Product Name");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 791), (int)(offset_Y + 149), 30, 0, "微软雅黑", 1, 400, false, false, false, "1MORE Piston Fit Bluetooth ");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 103), (int)(offset_Y + 1081), 25, 0, "微软雅黑", 1, 400, false, false, false, "备注");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 69), (int)(offset_Y + 1104), 33, 0, "微软雅黑", 1, 400, false, false, false, "Remark");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 59), (int)(offset_Y + 269), 30, 0, "微软雅黑", 1, 400, false, false, false, "采购订单号/PO");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 65), (int)(offset_Y + 332), 30, 0, "微软雅黑", 1, 400, false, false, false, "包装规格/Q'ty");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 463), (int)(offset_Y + 333), 30, 0, "微软雅黑", 1, 400, false, false, false, "  60PCS/箱");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 950), (int)(offset_Y + 321), 30, 0, "微软雅黑", 1, 400, false, false, false, "Product Color");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 76), (int)(offset_Y + 400), 30, 0, "微软雅黑", 1, 400, false, false, false, "产品料号/PN");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 54), (int)(offset_Y + 469), 30, 0, "微软雅黑", 1, 400, false, false, false, "外尺寸Size(cm*cm*cm)");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 55), (int)(offset_Y + 540), 30, 0, "微软雅黑", 1, 400, false, false, false, "检验员/Inspector");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 58), (int)(offset_Y + 596), 30, 0, "微软雅黑", 1, 400, false, false, false, "检验批次/Lot NO.");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 47), (int)(offset_Y + 645), 30, 0, "微软雅黑", 1, 400, false, false, false, "出厂日期/Ex-factory Date");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 146), (int)(offset_Y + 702), 30, 0, "微软雅黑", 1, 400, false, false, false, "UPC code");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 153), (int)(offset_Y + 767), 30, 0, "微软雅黑", 1, 400, false, false, false, "商品条码");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 94), (int)(offset_Y + 804), 30, 0, "微软雅黑", 1, 400, false, false, false, "Commercial Code");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 185), (int)(offset_Y + 862), 30, 0, "微软雅黑", 1, 400, false, false, false, "箱号");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 94), (int)(offset_Y + 903), 30, 0, "微软雅黑", 1, 400, false, false, false, "Commodity Code");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }


           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 65), (int)(offset_Y + 953), 30, 0, "微软雅黑", 1, 400, false, false, false, "毛重（Kg)");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 96), (int)(offset_Y + 986), 30, 0, "微软雅黑", 1, 400, false, false, false, "G.W");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 278), (int)(offset_Y + 953), 30, 0, "微软雅黑", 1, 400, false, false, false, "净重（Kg）");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 495), (int)(offset_Y + 953), 30, 0, "微软雅黑", 1, 400, false, false, false, "体积（m3)");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 535), (int)(offset_Y + 986), 30, 0, "微软雅黑", 1, 400, false, false, false, "Bulk");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 324), (int)(offset_Y + 986), 30, 0, "微软雅黑", 1, 400, false, false, false, "N.W.");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 748), (int)(offset_Y + 950), 30, 0, "微软雅黑", 1, 400, false, false, false, "目的地");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 714), (int)(offset_Y + 986), 30, 0, "微软雅黑", 1, 400, false, false, false, "Destination ");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 1026), (int)(offset_Y + 950), 30, 0, "微软雅黑", 1, 400, false, false, false, "仓库");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 977), (int)(offset_Y + 986), 30, 0, "微软雅黑", 1, 400, false, false, false, "Warehouse");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 568), (int)(offset_Y + 14), 42, 0, "微软雅黑", 1, 400, false, false, false, "测试声学科技有限公司");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 448), (int)(offset_Y + 62), 38, 0, "微软雅黑", 1, 400, false, false, false, "TEST Acoustic Technology Limited");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 539), (int)(offset_Y + 400), 30, 0, "微软雅黑", 1, 400, false, false, false, "9900100514-1");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 530), (int)(offset_Y + 476), 30, 0, "微软雅黑", 1, 400, false, false, false, "51.5*36.0*43.5cm");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 646), (int)(offset_Y + 907), 29, 0, "微软雅黑", 1, 400, false, false, false, "13E1028BTS0002649");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 793), (int)(offset_Y + 194), 30, 0, "微软雅黑", 1, 400, false, false, false, "In-ear Headphones ");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 540), (int)(offset_Y + 1037), 30, 0, "微软雅黑", 1, 400, false, false, false, "0.08");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 950), (int)(offset_Y + 477), 30, 0, "微软雅黑", 1, 400, false, false, false, "钛色/Titanium");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 552), (int)(offset_Y + 542), 30, 0, "微软雅黑", 1, 400, false, false, false, "youfang Duan");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 985), (int)(offset_Y + 280), 30, 0, "微软雅黑", 1, 400, false, false, false, "产品颜色");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 81), (int)(offset_Y + 1036), 29, 0, "微软雅黑", 1, 400, false, false, false, "12.00");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }

           /*打印文字*/
           result_printing = PrintLab.PTK_DrawText_TrueType((int)(offset_X + 311), (int)(offset_Y + 1036), 29, 0, "微软雅黑", 1, 400, false, false, false, "10.65");
           if (result_printing != 0) { Printers.showError("PTK_DrawText_TrueType", result_printing); return; }


           /*打印，必须要执行这一行，否则不会打印，打印多张标签时每张标签后面都需要加它*/
           result_printing = PrintLab.PTK_PrintLabel(1, 1);
           if (result_printing != 0) { Printers.showError("PTK_PrintLabel", result_printing); return; }

          

           /*断开打印机连接*/
           PrintLab.PTK_CloseConnect();


           System.err.println("执行成功！");
       }
	  

}
