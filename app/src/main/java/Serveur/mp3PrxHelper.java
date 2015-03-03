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

public final class mp3PrxHelper extends Ice.ObjectPrxHelperBase implements mp3Prx
{
    private static final String __ajouterMP3_name = "ajouterMP3";

    public void ajouterMP3(String nom, String url)
    {
        ajouterMP3(nom, url, null, false);
    }

    public void ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx)
    {
        ajouterMP3(nom, url, __ctx, true);
    }

    private void ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "ajouterMP3", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __delBase = __getDelegate(false);
                    _mp3Del __del = (_mp3Del)__delBase;
                    __del.ajouterMP3(nom, url, __ctx, __observer);
                    return;
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url)
    {
        return begin_ajouterMP3(nom, url, null, false, null);
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx)
    {
        return begin_ajouterMP3(nom, url, __ctx, true, null);
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url, Ice.Callback __cb)
    {
        return begin_ajouterMP3(nom, url, null, false, __cb);
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_ajouterMP3(nom, url, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url, Callback_mp3_ajouterMP3 __cb)
    {
        return begin_ajouterMP3(nom, url, null, false, __cb);
    }

    public Ice.AsyncResult begin_ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx, Callback_mp3_ajouterMP3 __cb)
    {
        return begin_ajouterMP3(nom, url, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_ajouterMP3(String nom, String url, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __ajouterMP3_name, __cb);
        try
        {
            __result.__prepare(__ajouterMP3_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(nom);
            __os.writeString(url);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public void end_ajouterMP3(Ice.AsyncResult __result)
    {
        __end(__result, __ajouterMP3_name);
    }

    private static final String __jouerMP3_name = "jouerMP3";

    public void jouerMP3(String nom)
    {
        jouerMP3(nom, null, false);
    }

    public void jouerMP3(String nom, java.util.Map<String, String> __ctx)
    {
        jouerMP3(nom, __ctx, true);
    }

    private void jouerMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "jouerMP3", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __delBase = __getDelegate(false);
                    _mp3Del __del = (_mp3Del)__delBase;
                    __del.jouerMP3(nom, __ctx, __observer);
                    return;
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_jouerMP3(String nom)
    {
        return begin_jouerMP3(nom, null, false, null);
    }

    public Ice.AsyncResult begin_jouerMP3(String nom, java.util.Map<String, String> __ctx)
    {
        return begin_jouerMP3(nom, __ctx, true, null);
    }

    public Ice.AsyncResult begin_jouerMP3(String nom, Ice.Callback __cb)
    {
        return begin_jouerMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_jouerMP3(String nom, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_jouerMP3(nom, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_jouerMP3(String nom, Callback_mp3_jouerMP3 __cb)
    {
        return begin_jouerMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_jouerMP3(String nom, java.util.Map<String, String> __ctx, Callback_mp3_jouerMP3 __cb)
    {
        return begin_jouerMP3(nom, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_jouerMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __jouerMP3_name, __cb);
        try
        {
            __result.__prepare(__jouerMP3_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(nom);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public void end_jouerMP3(Ice.AsyncResult __result)
    {
        __end(__result, __jouerMP3_name);
    }

    private static final String __listerMP3_name = "listerMP3";

    public String[] listerMP3()
    {
        return listerMP3(null, false);
    }

    public String[] listerMP3(java.util.Map<String, String> __ctx)
    {
        return listerMP3(__ctx, true);
    }

    private String[] listerMP3(java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "listerMP3", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("listerMP3");
                    __delBase = __getDelegate(false);
                    _mp3Del __del = (_mp3Del)__delBase;
                    return __del.listerMP3(__ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_listerMP3()
    {
        return begin_listerMP3(null, false, null);
    }

    public Ice.AsyncResult begin_listerMP3(java.util.Map<String, String> __ctx)
    {
        return begin_listerMP3(__ctx, true, null);
    }

    public Ice.AsyncResult begin_listerMP3(Ice.Callback __cb)
    {
        return begin_listerMP3(null, false, __cb);
    }

    public Ice.AsyncResult begin_listerMP3(java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_listerMP3(__ctx, true, __cb);
    }

    public Ice.AsyncResult begin_listerMP3(Callback_mp3_listerMP3 __cb)
    {
        return begin_listerMP3(null, false, __cb);
    }

    public Ice.AsyncResult begin_listerMP3(java.util.Map<String, String> __ctx, Callback_mp3_listerMP3 __cb)
    {
        return begin_listerMP3(__ctx, true, __cb);
    }

    private Ice.AsyncResult begin_listerMP3(java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__listerMP3_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __listerMP3_name, __cb);
        try
        {
            __result.__prepare(__listerMP3_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            __result.__writeEmptyParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public String[] end_listerMP3(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __listerMP3_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            String[] __ret;
            __ret = listMP3Helper.read(__is);
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __rechercherMP3_name = "rechercherMP3";

    public boolean rechercherMP3(String nom)
    {
        return rechercherMP3(nom, null, false);
    }

    public boolean rechercherMP3(String nom, java.util.Map<String, String> __ctx)
    {
        return rechercherMP3(nom, __ctx, true);
    }

    private boolean rechercherMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "rechercherMP3", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("rechercherMP3");
                    __delBase = __getDelegate(false);
                    _mp3Del __del = (_mp3Del)__delBase;
                    return __del.rechercherMP3(nom, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom)
    {
        return begin_rechercherMP3(nom, null, false, null);
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom, java.util.Map<String, String> __ctx)
    {
        return begin_rechercherMP3(nom, __ctx, true, null);
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom, Ice.Callback __cb)
    {
        return begin_rechercherMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_rechercherMP3(nom, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom, Callback_mp3_rechercherMP3 __cb)
    {
        return begin_rechercherMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_rechercherMP3(String nom, java.util.Map<String, String> __ctx, Callback_mp3_rechercherMP3 __cb)
    {
        return begin_rechercherMP3(nom, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_rechercherMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__rechercherMP3_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __rechercherMP3_name, __cb);
        try
        {
            __result.__prepare(__rechercherMP3_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(nom);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public boolean end_rechercherMP3(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __rechercherMP3_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    private static final String __supprimerMP3_name = "supprimerMP3";

    public boolean supprimerMP3(String nom)
    {
        return supprimerMP3(nom, null, false);
    }

    public boolean supprimerMP3(String nom, java.util.Map<String, String> __ctx)
    {
        return supprimerMP3(nom, __ctx, true);
    }

    private boolean supprimerMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        final Ice.Instrumentation.InvocationObserver __observer = IceInternal.ObserverHelper.get(this, "supprimerMP3", __ctx);
        int __cnt = 0;
        try
        {
            while(true)
            {
                Ice._ObjectDel __delBase = null;
                try
                {
                    __checkTwowayOnly("supprimerMP3");
                    __delBase = __getDelegate(false);
                    _mp3Del __del = (_mp3Del)__delBase;
                    return __del.supprimerMP3(nom, __ctx, __observer);
                }
                catch(IceInternal.LocalExceptionWrapper __ex)
                {
                    __handleExceptionWrapper(__delBase, __ex, __observer);
                }
                catch(Ice.LocalException __ex)
                {
                    __cnt = __handleException(__delBase, __ex, null, __cnt, __observer);
                }
            }
        }
        finally
        {
            if(__observer != null)
            {
                __observer.detach();
            }
        }
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom)
    {
        return begin_supprimerMP3(nom, null, false, null);
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom, java.util.Map<String, String> __ctx)
    {
        return begin_supprimerMP3(nom, __ctx, true, null);
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom, Ice.Callback __cb)
    {
        return begin_supprimerMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom, java.util.Map<String, String> __ctx, Ice.Callback __cb)
    {
        return begin_supprimerMP3(nom, __ctx, true, __cb);
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom, Callback_mp3_supprimerMP3 __cb)
    {
        return begin_supprimerMP3(nom, null, false, __cb);
    }

    public Ice.AsyncResult begin_supprimerMP3(String nom, java.util.Map<String, String> __ctx, Callback_mp3_supprimerMP3 __cb)
    {
        return begin_supprimerMP3(nom, __ctx, true, __cb);
    }

    private Ice.AsyncResult begin_supprimerMP3(String nom, java.util.Map<String, String> __ctx, boolean __explicitCtx, IceInternal.CallbackBase __cb)
    {
        __checkAsyncTwowayOnly(__supprimerMP3_name);
        IceInternal.OutgoingAsync __result = new IceInternal.OutgoingAsync(this, __supprimerMP3_name, __cb);
        try
        {
            __result.__prepare(__supprimerMP3_name, Ice.OperationMode.Normal, __ctx, __explicitCtx);
            IceInternal.BasicStream __os = __result.__startWriteParams(Ice.FormatType.DefaultFormat);
            __os.writeString(nom);
            __result.__endWriteParams();
            __result.__send(true);
        }
        catch(Ice.LocalException __ex)
        {
            __result.__exceptionAsync(__ex);
        }
        return __result;
    }

    public boolean end_supprimerMP3(Ice.AsyncResult __result)
    {
        Ice.AsyncResult.__check(__result, this, __supprimerMP3_name);
        boolean __ok = __result.__wait();
        try
        {
            if(!__ok)
            {
                try
                {
                    __result.__throwUserException();
                }
                catch(Ice.UserException __ex)
                {
                    throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                }
            }
            IceInternal.BasicStream __is = __result.__startReadParams();
            boolean __ret;
            __ret = __is.readBool();
            __result.__endReadParams();
            return __ret;
        }
        catch(Ice.LocalException ex)
        {
            Ice.Instrumentation.InvocationObserver __obsv = __result.__getObserver();
            if(__obsv != null)
            {
                __obsv.failed(ex.ice_name());
            }
            throw ex;
        }
    }

    public static mp3Prx checkedCast(Ice.ObjectPrx __obj)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof mp3Prx)
            {
                __d = (mp3Prx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    mp3PrxHelper __h = new mp3PrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static mp3Prx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof mp3Prx)
            {
                __d = (mp3Prx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    mp3PrxHelper __h = new mp3PrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static mp3Prx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    mp3PrxHelper __h = new mp3PrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static mp3Prx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    mp3PrxHelper __h = new mp3PrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static mp3Prx uncheckedCast(Ice.ObjectPrx __obj)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof mp3Prx)
            {
                __d = (mp3Prx)__obj;
            }
            else
            {
                mp3PrxHelper __h = new mp3PrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static mp3Prx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        mp3Prx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            mp3PrxHelper __h = new mp3PrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::Serveur::mp3"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _mp3DelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _mp3DelD();
    }

    public static void __write(IceInternal.BasicStream __os, mp3Prx v)
    {
        __os.writeProxy(v);
    }

    public static mp3Prx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            mp3PrxHelper result = new mp3PrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}