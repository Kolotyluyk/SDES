package com.sk.sdes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Сергій on 06.11.2016.
 */

public class SDES {


    Key key;

    static ArrayList<String> ABC = new ArrayList(Arrays.asList("а", "б", "в", "г", "д", "е", "є", "ж", "з", "и", "і", "ї", "й"
            , "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф", "х", "ц","ч", "ш", "щ", "ь", "ю", "я"));

  String[][]S0={{"01","00","11","10"},
                {"11","10","01","00"},
                {"00","10","01","11"},
                {"11","01","11","01"}};

  String[][]S1={{"01","01","10","11"},
                {"10","00","01","11"},
                {"11","00","01","00"},
                {"10","01","00","11"}};


    public SDES() {

    }
    public void setKey(Key key) {
        this.key = key;
    }
    private byte[] IP(byte[] text){
        byte[] resultbyte=new byte[8];
        resultbyte[0]=text[1];
        resultbyte[1]=text[5];
        resultbyte[2]=text[2];
        resultbyte[3]=text[0];
        resultbyte[4]=text[3];
        resultbyte[5]=text[7];
        resultbyte[6]=text[4];
        resultbyte[7]=text[6];
        return resultbyte;
    }

    private byte[] Fk(byte[] text,byte[] key){
        byte[] Lbyte=new byte[8];
        byte[] Rbyte=new byte[8];


        Rbyte[0]= (byte) (text[7]^key[0]);
        Rbyte[1]= (byte) (text[4]^key[1]);
        Rbyte[2]= (byte) (text[5]^key[2]);
        Rbyte[3]= (byte) (text[6]^key[3]);
        Rbyte[4]= (byte) (text[5]^key[4]);
        Rbyte[5]= (byte) (text[6]^key[5]);
        Rbyte[6]= (byte) (text[7]^key[6]);
        Rbyte[7]= (byte) (text[4]^key[7]);

        text=F(Rbyte,text);

          return text;
    }

    private byte[] F(byte[] Partoftext,byte[] text){
        byte[] P4Byte=new byte[4];


        int row=Partoftext[0]*2+Partoftext[3];
        int column=Partoftext[1]*2+Partoftext[2];
        int ss= Integer.parseInt(S0[row][column]);
        P4Byte[3]= (byte) (ss/10);
        P4Byte[0]= (byte) (ss%10);


         row=Partoftext[4]*2+Partoftext[7];
         column=Partoftext[5]*2+Partoftext[6];

        ss= Integer.parseInt(S1[row][column]);
        P4Byte[2]= (byte) (ss/10);
        P4Byte[1]= (byte) (ss%10);

        text[0]= (byte) (text[0]^P4Byte[1]);
        text[1]= (byte) (text[1]^P4Byte[3]);
        text[2]= (byte) (text[2]^P4Byte[2]);
        text[3]= (byte) (text[3]^P4Byte[0]);



        return text;
    }

    private byte[] SW(byte[] text){
      byte[] resByte =new byte[8];
        for (int i=0;i<4;i++){
            resByte[i]=text[i+4];
            resByte[i+4]=text[i];
        }

        return resByte;
    }

    private byte[] negativIP(byte[] text){
        byte[] resultbyte=new byte[8];
        resultbyte[0]=text[3];
        resultbyte[1]=text[0];
        resultbyte[2]=text[2];
        resultbyte[3]=text[4];
        resultbyte[4]=text[6];
        resultbyte[5]=text[1];
        resultbyte[6]=text[7];
        resultbyte[7]=text[5];
        return resultbyte;
    }

    private byte[] encrypt(byte[] text){
        byte[] interimTExt=IP(text);
        interimTExt=Fk(interimTExt,key.getFirstKey());
        interimTExt=SW(interimTExt);
        interimTExt=Fk(interimTExt,key.getSecondKey());
        interimTExt=negativIP(interimTExt);
        return     interimTExt;
    }

    private  byte[] decrupt( byte[] text){
        byte[] interimTExt=IP(text);
        interimTExt=Fk(interimTExt,key.getSecondKey());
        interimTExt=SW(interimTExt);
        interimTExt=Fk(interimTExt,key.getFirstKey());
        interimTExt=negativIP(interimTExt);


        return     interimTExt;
    }




    /////////////////////  decimal to array bin
    private byte[] binToByteArray(int index){
        byte[] resByte=new byte[8];
      for (int i=0;i<8;i++) {
          resByte[7-i]= (byte) (index%2);
          index/=2;
      }
       return resByte;
    }

    private int byteArrayToInt(byte[] text) {
        int index=0;
                                                            /////////
        for (int i=text.length;i>0;i--) {
            index+=text[i-1]*Math.pow(2,text.length-i);

        }
        return index;

    }

    public String decrupt(String text){

        String resultText="";
        for (int i=0;i<text.length();i++)
        {
            int index;
            if (ABC.contains(String.valueOf(text.charAt(i))))
                index=ABC.indexOf(String.valueOf(text.charAt(i)));
            else
                index = (text.charAt(i));


            int a=byteArrayToInt(decrupt(binToByteArray(index)));

            if(a<33)
                resultText+=ABC.get(a);
            else
                resultText += (char)(a);

        }

        return resultText;
    }

    public String encrupt(String text){

        String resultText="";

        for (int i=0;i<text.length();i++)
           {
               int index;
               if (ABC.contains(String.valueOf(text.charAt(i))))
                   index=ABC.indexOf(String.valueOf(text.charAt(i)));
               else
                index = (text.charAt(i));


               int a=byteArrayToInt(encrypt(binToByteArray(index)));

               if(a<33)
                   resultText+=ABC.get(a);
               else
                resultText += (char)(a);

            }


        return resultText;
    }
}
