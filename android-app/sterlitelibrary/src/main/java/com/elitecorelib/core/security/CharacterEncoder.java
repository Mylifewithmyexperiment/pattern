package com.elitecorelib.core.security;

import java.io.*;
import java.nio.ByteBuffer;

public abstract class CharacterEncoder
{

    protected PrintStream pStream;

    public CharacterEncoder()
    {
    }

    protected abstract int bytesPerAtom();

    protected abstract int bytesPerLine();

    protected void encodeBufferPrefix(OutputStream aStream)
        throws IOException
    {
        pStream = new PrintStream(aStream);
    }

    protected void encodeBufferSuffix(OutputStream outputstream)
        throws IOException
    {
    }

    protected void encodeLinePrefix(OutputStream outputstream, int i)
        throws IOException
    {
    }

    protected void encodeLineSuffix(OutputStream aStream)
        throws IOException
    {
        pStream.println();
    }

    protected abstract void encodeAtom(OutputStream outputstream, byte abyte0[], int i, int j)
        throws IOException;

    protected int readFully(InputStream in, byte buffer[])
        throws IOException
    {
        for(int i = 0; i < buffer.length; i++)
        {
            int q = in.read();
            if(q == -1)
            {
                return i;
            }
            buffer[i] = (byte)q;
        }

        return buffer.length;
    }

    public void encode(InputStream inStream, OutputStream outStream)
        throws IOException
    {
        byte tmpbuffer[] = new byte[bytesPerLine()];
        encodeBufferPrefix(outStream);
        do
        {
            int numBytes = readFully(inStream, tmpbuffer);
            if(numBytes == 0)
            {
                break;
            }
            encodeLinePrefix(outStream, numBytes);
            for(int j = 0; j < numBytes; j += bytesPerAtom())
            {
                if(j + bytesPerAtom() <= numBytes)
                {
                    encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
                } else
                {
                    encodeAtom(outStream, tmpbuffer, j, numBytes - j);
                }
            }

            if(numBytes < bytesPerLine())
            {
                break;
            }
            encodeLineSuffix(outStream);
        } while(true);
        encodeBufferSuffix(outStream);
    }

    public void encode(byte aBuffer[], OutputStream aStream)
        throws IOException
    {
        ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
        encode(((InputStream) (inStream)), aStream);
    }

    public String encode(byte aBuffer[])
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
        String retVal = null;
        try
        {
            encode(((InputStream) (inStream)), ((OutputStream) (outStream)));
            retVal = outStream.toString("8859_1");
        }
        catch(Exception IOException)
        {
            throw new Error("CharacterEncoder.encode internal error");
        }
        return retVal;
    }

    private byte[] getBytes(ByteBuffer bb)
    {
        byte buf[] = (byte[])null;
        if(bb.hasArray())
        {
            byte tmp[] = bb.array();
            if(tmp.length == bb.capacity() && tmp.length == bb.remaining())
            {
                buf = tmp;
                bb.position(bb.limit());
            }
        }
        if(buf == null)
        {
            buf = new byte[bb.remaining()];
            bb.get(buf);
        }
        return buf;
    }

    public void encode(ByteBuffer aBuffer, OutputStream aStream)
        throws IOException
    {
        byte buf[] = getBytes(aBuffer);
        encode(buf, aStream);
    }

    public String encode(ByteBuffer aBuffer)
    {
        byte buf[] = getBytes(aBuffer);
        return encode(buf);
    }

    public void encodeBuffer(InputStream inStream, OutputStream outStream)
        throws IOException
    {
        byte tmpbuffer[] = new byte[bytesPerLine()];
        encodeBufferPrefix(outStream);
        int numBytes;
        do
        {
            numBytes = readFully(inStream, tmpbuffer);
            if(numBytes == 0)
            {
                break;
            }
            encodeLinePrefix(outStream, numBytes);
            for(int j = 0; j < numBytes; j += bytesPerAtom())
            {
                if(j + bytesPerAtom() <= numBytes)
                {
                    encodeAtom(outStream, tmpbuffer, j, bytesPerAtom());
                } else
                {
                    encodeAtom(outStream, tmpbuffer, j, numBytes - j);
                }
            }

            encodeLineSuffix(outStream);
        } while(numBytes >= bytesPerLine());
        encodeBufferSuffix(outStream);
    }

    public void encodeBuffer(byte aBuffer[], OutputStream aStream)
        throws IOException
    {
        ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
        encodeBuffer(((InputStream) (inStream)), aStream);
    }

    public String encodeBuffer(byte aBuffer[])
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ByteArrayInputStream inStream = new ByteArrayInputStream(aBuffer);
        try
        {
            encodeBuffer(((InputStream) (inStream)), ((OutputStream) (outStream)));
        }
        catch(Exception IOException)
        {
            throw new Error("CharacterEncoder.encodeBuffer internal error");
        }
        return outStream.toString();
    }

    public void encodeBuffer(ByteBuffer aBuffer, OutputStream aStream)
        throws IOException
    {
        byte buf[] = getBytes(aBuffer);
        encodeBuffer(buf, aStream);
    }

    public String encodeBuffer(ByteBuffer aBuffer)
    {
        byte buf[] = getBytes(aBuffer);
        return encodeBuffer(buf);
    }
}
