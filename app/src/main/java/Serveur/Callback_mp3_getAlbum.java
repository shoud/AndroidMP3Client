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

public abstract class Callback_mp3_getAlbum extends Ice.TwowayCallback
{
    public abstract void response(String __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        mp3Prx __proxy = (mp3Prx)__result.getProxy();
        String __ret = null;
        try
        {
            __ret = __proxy.end_getAlbum(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
