// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `ICEserveurMP3.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Serveur;

public abstract class _mp3Disp extends Ice.ObjectImpl implements mp3
{
    protected void
    ice_copyStateFrom(Ice.Object __obj)
        throws java.lang.CloneNotSupportedException
    {
        throw new java.lang.CloneNotSupportedException();
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::Serveur::mp3"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[1];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[1];
    }

    public static String ice_staticId()
    {
        return __ids[1];
    }

    public final void ajouterMP3(String nom, String url)
    {
        ajouterMP3(nom, url, null);
    }

    public final void envoyerMusique(String nom, byte[] musique)
    {
        envoyerMusique(nom, musique, null);
    }

    public final String getToken()
    {
        return getToken(null);
    }

    public final String jouerMP3(String nom)
    {
        return jouerMP3(nom, null);
    }

    public final String[] listerMP3()
    {
        return listerMP3(null);
    }

    public final void play()
    {
        play(null);
    }

    public final boolean rechercherMP3(String nom)
    {
        return rechercherMP3(nom, null);
    }

    public final void stop()
    {
        stop(null);
    }

    public final boolean supprimerMP3(String nom)
    {
        return supprimerMP3(nom, null);
    }

    public static Ice.DispatchStatus ___ajouterMP3(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String nom;
        String url;
        nom = __is.readString();
        url = __is.readString();
        __inS.endReadParams();
        __obj.ajouterMP3(nom, url, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___supprimerMP3(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String nom;
        nom = __is.readString();
        __inS.endReadParams();
        boolean __ret = __obj.supprimerMP3(nom, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___rechercherMP3(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String nom;
        nom = __is.readString();
        __inS.endReadParams();
        boolean __ret = __obj.rechercherMP3(nom, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeBool(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___jouerMP3(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String nom;
        nom = __is.readString();
        __inS.endReadParams();
        String __ret = __obj.jouerMP3(nom, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___getToken(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        String __ret = __obj.getToken(__current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __os.writeString(__ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___play(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        __obj.play(__current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___stop(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        __obj.stop(__current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___envoyerMusique(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        String nom;
        byte[] musique;
        nom = __is.readString();
        musique = MusiqueByteHelper.read(__is);
        __inS.endReadParams();
        __obj.envoyerMusique(nom, musique, __current);
        __inS.__writeEmptyParams();
        return Ice.DispatchStatus.DispatchOK;
    }

    public static Ice.DispatchStatus ___listerMP3(mp3 __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        __inS.readEmptyParams();
        String[] __ret = __obj.listerMP3(__current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        listMP3Helper.write(__os, __ret);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    private final static String[] __all =
    {
        "ajouterMP3",
        "envoyerMusique",
        "getToken",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "jouerMP3",
        "listerMP3",
        "play",
        "rechercherMP3",
        "stop",
        "supprimerMP3"
    };

    public Ice.DispatchStatus __dispatch(IceInternal.Incoming in, Ice.Current __current)
    {
        int pos = java.util.Arrays.binarySearch(__all, __current.operation);
        if(pos < 0)
        {
            throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return ___ajouterMP3(this, in, __current);
            }
            case 1:
            {
                return ___envoyerMusique(this, in, __current);
            }
            case 2:
            {
                return ___getToken(this, in, __current);
            }
            case 3:
            {
                return ___ice_id(this, in, __current);
            }
            case 4:
            {
                return ___ice_ids(this, in, __current);
            }
            case 5:
            {
                return ___ice_isA(this, in, __current);
            }
            case 6:
            {
                return ___ice_ping(this, in, __current);
            }
            case 7:
            {
                return ___jouerMP3(this, in, __current);
            }
            case 8:
            {
                return ___listerMP3(this, in, __current);
            }
            case 9:
            {
                return ___play(this, in, __current);
            }
            case 10:
            {
                return ___rechercherMP3(this, in, __current);
            }
            case 11:
            {
                return ___stop(this, in, __current);
            }
            case 12:
            {
                return ___supprimerMP3(this, in, __current);
            }
        }

        assert(false);
        throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = 0L;
}
