package com.sk.sdes;

/**
 * Created by Сергій on 06.11.2016.
 */
public class Key {

   private byte[] firstKey;
   private byte[] secondKey;
   private String initialKey;

    public void setInitialKey(String initialKey) {
        this.initialKey = initialKey;
    }



    public byte[] getFirstKey() {
        return firstKey;

    }

    public byte[] getSecondKey() {
        return secondKey;

    }


    public Key() {
    }

    public void setKey(String initialKey) {
        this.initialKey=initialKey;
        int a=Integer.parseInt(initialKey);                   ////////
        byte[] q=binToByteArray(a);                                 ////////
        byte interimKey[]=P10(q);
        interimKey=Shift(interimKey);
        firstKey=P8(interimKey);
        interimKey=Shift(interimKey);
        secondKey=P8(interimKey);
    }


    private byte[] binToByteArray(int index){
        byte[] resByte=new byte[10];
        for (int i=0;i<10;i++) {
            resByte[9-i]= (byte) (index%2);
            index/=2;
        }
        return resByte;
    }

    private byte[] P10(byte[] key){
        byte[] resultbyte=new byte[10];

        resultbyte[0]=key[2];
        resultbyte[1]=key[4];
        resultbyte[2]=key[1];
        resultbyte[3]=key[6];
        resultbyte[4]=key[3];
        resultbyte[5]=key[9];
        resultbyte[6]=key[0];
        resultbyte[7]=key[8];
        resultbyte[8]=key[7];
        resultbyte[9]=key[5];



        return resultbyte;
    }

    private byte[] Shift(byte[] key){



        byte midlebyte=key[0];
        byte lastbyte=key[key.length/2];

        for (int i=0;i<key.length-1;i++){
            key[i]=key[i+1];
        }
        key[key.length/2-1]=midlebyte;
        key[key.length-1]=lastbyte;
        return key;
    }


    private byte[] P8(byte[] key) {
        byte[] resultbyte = new byte[8];

        ////////////////////////////////
        int cooficient=key.length%8;

        resultbyte[0] = key[cooficient+3];
        resultbyte[1] = key[cooficient+0];
        resultbyte[2] = key[cooficient+4];
        resultbyte[3] = key[cooficient+1];
        resultbyte[4] = key[cooficient+5];
        resultbyte[5] = key[cooficient+2];
        resultbyte[6] = key[cooficient+7];
        resultbyte[7] = key[cooficient+6];

        return resultbyte;
    }
}

