/*******************************************************************************
 * Copyright (C) 2017 Bstek.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.bstek.ureport.build.compute;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.bstek.ureport.build.BindData;
import com.bstek.ureport.build.Context;
import com.bstek.ureport.definition.value.Source;
import com.bstek.ureport.definition.value.ValueType;
import com.bstek.ureport.definition.value.ZxingValue;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.expression.model.Expression;
import com.bstek.ureport.expression.model.data.ExpressionData;
import com.bstek.ureport.model.Cell;
import com.bstek.ureport.model.Image;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author Jacky.gao
 * @since 2017年3月27日
 */
public class ZxingValueCompute implements ValueCompute {
	private static final int BLACK = 0xff000000;  
    private static final int WHITE = 0xFFFFFFFF;
	@Override
	public List<BindData> compute(Cell cell, Context context) {
		List<BindData> list=new ArrayList<BindData>();
		ZxingValue value=(ZxingValue)cell.getValue();
		String format=value.getFormat();
		BarcodeFormat barcodeForamt=BarcodeFormat.QR_CODE;
		if(StringUtils.isNotBlank(format)){
			barcodeForamt=BarcodeFormat.valueOf(format);			
		}
		int w=value.getWidth();
		int h=value.getHeight();
		Source source=value.getSource();
		if(source.equals(Source.text)){
			String data=value.getValue();
			Image image=buildImage(barcodeForamt,data,w,h);
			list.add(new BindData(image));
		}else{
			Expression expression=value.getExpression();
			ExpressionData<?> data=expression.execute(cell,cell, context);
			Object obj=data.getData();
			if(obj instanceof List){
				List<?> listData=(List<?>)obj;
				for(Object o:listData){
					if(o!=null){
						Image image=buildImage(barcodeForamt,o.toString(),w,h);
						list.add(new BindData(image));						
					}
				}
			}else if(obj instanceof String){
				String text=obj.toString();
				if(text.startsWith("\"") && text.endsWith("\"")){
					text=text.substring(1,text.length()-1);
				}
				Image image=buildImage(barcodeForamt,text,w,h);
				list.add(new BindData(image));			
			}else{
				if(obj!=null){
					Image image=buildImage(barcodeForamt,obj.toString(),w,h);
					list.add(new BindData(image));					
				}
			}
		}
		return list;
	}
	
	private Image buildImage(BarcodeFormat format,String data,int w,int h){
        try{
        	Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN,0);
            if(format.equals(BarcodeFormat.QR_CODE)){
            	hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);            	
            }
            BitMatrix matrix = new MultiFormatWriter().encode(data,format, w, h,hints);
            int width = matrix.getWidth();  
            int height = matrix.getHeight();
            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < width; x++) {
            	for (int y = 0; y < height; y++) {
            		image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            	}
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] bytes=outputStream.toByteArray();
            String base64Data=Base64Utils.encodeToString(bytes);
            IOUtils.closeQuietly(outputStream);
            return new Image(base64Data,w,h);
        }catch(Exception ex){
        	throw new ReportComputeException(ex);
        }
	}

	@Override
	public ValueType type() {
		return ValueType.zxing;
	}
}
