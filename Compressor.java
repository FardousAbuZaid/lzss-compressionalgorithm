package LZSS;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.IOException;
/**
 * Final Project
 * Submitted by: 
 * Student 1.Samar Abu Hdeeb 	ID# 211985957
 * Student 2.Mayar Altalalka    ID# 207243619
 * Student 3.Fardous Abu Zaid   ID# 211965108
 */

//Implementation of the LZSS compression algorithm, witch is our final project
public class Compressor {

	final int BACK_WINDOW_SIZE;

	final int MAXIMUN_MATCH_SIZE ;

	final int MINIMUN_MATCH_SIZE;

	Compressor(int back, int front, int match){

		BACK_WINDOW_SIZE = back;

		MAXIMUN_MATCH_SIZE = front;

		MINIMUN_MATCH_SIZE = match;

	}

	public void CompressLZSS(String[] input_names, String[] output_names) throws IOException {

		FileInputStream inFile = new FileInputStream(input_names[0]);

		FileOutputStream outFile = new FileOutputStream(output_names[0]);

		BufferedInputStream in = new BufferedInputStream(inFile);

		BufferedOutputStream outBuffer = new BufferedOutputStream(outFile);

		BitBuffer out = new BitBuffer(outBuffer, null);

		out.write(BACK_WINDOW_SIZE, 8);//write the back window size and max match(in bits)

		out.write(MAXIMUN_MATCH_SIZE, 8);

		int backWindowBytes = (int)Math.pow(2, BACK_WINDOW_SIZE);//calculate the size of window and max match

		int maxMatch = (int)Math.pow(2, MAXIMUN_MATCH_SIZE);

		StringBuffer backBuffer = new StringBuffer(backWindowBytes);//create string buffer for the back window

		int nextByte;//variable for the main loop

		String bestMatch = "";

		int index = -1;

		int tmpIndex = -1;	

		while((nextByte = in.read()) != -1) {

			if(nextByte < 0 )//read the next byte and check if positive

				nextByte+=256;//update if not positive

			tmpIndex = backBuffer.indexOf(bestMatch + (char)nextByte);//add char to best match and search in the window

			if(tmpIndex != -1 && bestMatch.length() + 1 < maxMatch ) {//if match found and the lookahead buffer have more characters

				bestMatch+=(char)nextByte;//update best match and index

				index = tmpIndex;

			}//go to check back window length

			else {//if match not found(end of main loop)

				if(bestMatch.length() >= MINIMUN_MATCH_SIZE) {//check if the best match is longer then the minimum size

					out.write("1");//write the first bit the represent a token 

					out.write(index, BACK_WINDOW_SIZE);//write the string index and length, the number of bits 

					out.write(bestMatch.length(), MAXIMUN_MATCH_SIZE);//depending the window and max match size

					tmpIndex = -1;//update variable and back window

					index = -1;

					backBuffer.append(bestMatch);

					bestMatch = "" + (char)(nextByte);

				}//go to check window length(end of main loop)

				else{//if best match size is smaller then the minimum match size(write the original byte)

					bestMatch+=(char)(nextByte);//update best match

					while((tmpIndex = backBuffer.indexOf(bestMatch)) == -1 && bestMatch != "") {//while match not found and best match not empty

						out.write("0");//write the first bit the represent a byte

						out.write((bestMatch.charAt(0)), 8);//write the byte value

						backBuffer.append(bestMatch.charAt(0));//update back window and best match

						bestMatch = bestMatch.substring(1);

					}					

				}//go to check window length(end of main loop)

			}

			if (backBuffer.length() > backWindowBytes)//check window size and delete the beginning of the window if need 

				backBuffer = backBuffer.delete(0, backBuffer.length() - backWindowBytes);

		}

		while(bestMatch.length() > 0 && bestMatch != "") {//if after read all bytes best match not empty

			if(index != -1 && bestMatch.length() > MINIMUN_MATCH_SIZE) {//if best match contain legal match

				out.write("1");//write match token

				out.write(index, BACK_WINDOW_SIZE);

				out.write(bestMatch.length(), MAXIMUN_MATCH_SIZE);

				bestMatch = "";

			}

			else {//write byte token

				out.write("0");

				out.write((byte)(bestMatch.charAt(0)), 8);

				bestMatch = bestMatch.substring(1);

			}

		}

		in.close();//close buffers

		out.close();

	}

	public void DecompressLZSS(String[] input_names, String[] output_names) throws IOException {		

		//Getting the file to decode

		FileInputStream inFile = new FileInputStream(input_names[0]);

		FileOutputStream outFile = new FileOutputStream(output_names[0]);

		BufferedInputStream inBuffer = new BufferedInputStream(inFile);

		BufferedOutputStream out = new BufferedOutputStream(outFile);

		BitBuffer in = new BitBuffer(null, inBuffer);

		inBuffer.read();inBuffer.read();

		final int BACK_WINDOW = inBuffer.read();

		final int MAX_MATCH = inBuffer.read();

		int backWindowByts = (int)Math.pow(2, BACK_WINDOW);//calculate the size in bytes

		StringBuffer backBuffer = new StringBuffer(backWindowByts);

		int nextIndex;//create variables for the main loop

		int nextLen;

		int nextBit;

		while((nextBit = in.read(1)) != -1) {//read the bit

			if(nextBit == 1) {//if nextbit equal to 1 do

				nextIndex = in.read(BACK_WINDOW);//read match token structure

				nextLen = in.read(MAX_MATCH);

				String toAdd = backBuffer.substring(nextIndex, nextIndex + nextLen);

				for(int i = 0; i < toAdd.length(); i++) {

					out.write(toAdd.charAt(i));//write the byte sequences to the decoded file

					backBuffer.append(toAdd.charAt(i));//update back window

				}

			}

			else {	

				//getting the next byte without decoding and write it to the decoded file

				nextIndex = in.read(8);

				if(nextIndex != -1)//if not end of file

					out.write(nextIndex);

				backBuffer.append((char)nextIndex);//update back window

			}

			if (backBuffer.length() > backWindowByts)//check window size and delete the beginning of the window if need 

				backBuffer = backBuffer.delete(0, backBuffer.length() - backWindowByts);	
		}

		in.close();//close buffers

		out.close();

	}

	public static void main(String[] args) throws IOException {

		String[] inPath = {args[0]};

		String[] outPath = {args[1]};

		Compressor compressor;

		int windowSize = (int)(Math.log(Integer.parseInt(args[5]))/Math.log(2)+1e-10);

		int maxLen = (int)(Math.log(Integer.parseInt(args[6]))/Math.log(2)+1e-10);

		int minLen = Integer.parseInt(args[7]);

		compressor = new Compressor(windowSize, maxLen, minLen);
		//encode the file

		compressor.CompressLZSS(inPath,outPath);

		compressor = new Compressor(0, 0, 0);

		//decode the file
		compressor.DecompressLZSS(inPath, outPath);



	}
}