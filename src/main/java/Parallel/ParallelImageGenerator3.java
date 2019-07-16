package Parallel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Forms.formDecompression;
import Others.Header;

public class ParallelImageGenerator3 implements Runnable{

	BufferedImage nuevaImagen;
	Header header;
	int row;
	Integer[] decoded;
	
	
	public ParallelImageGenerator3(Integer[] decoded, BufferedImage nuevaImagen, Header header, int row) {
		this.header = header;
		this.nuevaImagen = nuevaImagen;
		this.decoded = decoded;
		this.row = row;
	}
	
	public void write() {
		/*
		// position of block in block matrix
		int bPosX = bn % bWidth;
		int bPosY = bn / bHeight;
		
		// coords of block in image
		int bStartX = header.getX(0) * bPosX;
		int bStartY = header.getY(0) * bPosY;
		
		int blockWidth = header.getX(bn);
		int blockHeight = header.getY(bn);
		int where = 0;
		for(int y = bStartY; y < bStartY + blockHeight; y++) {
			for(int x = bStartX; x < bStartX + blockWidth; x++) {
				int color = 0;
				try {
					color = decoded.get(where);
				}catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				if(bn == (bWidth * bHeight - 1))
					System.out.println("Bloque " + bn + " en x " + x + " en y " + y);
				nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
				where++;
			}
		}
		
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
		System.out.println("END: " + bn);
		*/
	}

	@Override
	public void run() {
		// size of block matrix
		int bWidth = (int) Math.ceil((double) header.getWholeX() / (double) header.getX(0));
		System.out.println(bWidth);
		// iterate all the blocks in that row
		int actualBlock = row * bWidth;
		int bStartY = header.getY(0) * row;
		
		// first block starts writing on decoded position...
		int where = 0;
		for(int j = 0; j < actualBlock; j++) {
			where += (header.getX(j) * header.getY(j));
		}
		//System.out.println("ROW " + row + " STARTS ON " + where);
		for(int i = 0; i < bWidth; i++) {
			//System.out.println("ROW " + row + " IS WRITING BLOCK " + actualBlock);
			// see where each block starts writing
			int bStartX = header.getX(0) * i;
			//System.out.println("ROW " + row + " IS WRITING BLOCK " + actualBlock + " on start pos " + bStartX);
			for(int y = bStartY; y < bStartY + header.getY(actualBlock); y++) {
				for(int x = bStartX; x < bStartX + header.getX(actualBlock); x++) {
					int color = 0;
					try {
						color = decoded[where];
					}catch(IndexOutOfBoundsException e) {
						e.printStackTrace();
					}
					nuevaImagen.setRGB(x, y, new Color(color, color, color).getRGB());
					where++;
				}
			}
			actualBlock++;
		}	
		synchronized(formDecompression.progressBar) {
			formDecompression.progressBar.setValue(formDecompression.progressBar.getValue() + 1);
		}
	}

}