package org.ppl.BaseClass;

import org.ppl.core.PObject;

public abstract class BaseThread extends PObject {	
	public abstract void Run();
	public abstract boolean isRun();
	public abstract boolean Stop();
	
	public void name() {
		
	}
}
