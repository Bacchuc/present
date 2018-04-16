package com.present.common.util.jwt;


public enum TokenState {
	
	EXPIRED("EXPIRED"),	
	INVALID("INVALID"), 	
	VALID("VALID");
	
	private String  state; 
	
    private TokenState(String state) {  
    	this.state = state;  
    }
    
    /**
     * 根据状态字符串获取token状态枚举对象
     * @param tokenState
     * @return
     */
    public static TokenState getTokenState(String tokenState){
    	TokenState[] states=TokenState.values();
    	TokenState ts=null;
    	for (TokenState state : states) {
			if(state.toString().equals(tokenState)){
				ts=state;
				break;
			}
		}
    	return ts;
    }
    
    public String toString() {
    	return this.state;
    }
    
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
