package com.quinn.xmpp;

import java.io.Serializable;

import android.content.Intent;

public class Intents {

	public static final String INTENT_PREFIX = "com.quinn.xmpp.";
	public static final String INTENT_EXTRA_PREFIX = INTENT_PREFIX + "extra.";

	public static class Builder {
		
		
		private final Intent intent;

		public Builder(String actionSuffix) {
			intent = new Intent(INTENT_PREFIX + actionSuffix);
		}
		
		public Builder add(String keyNanme, String value){
			intent.putExtra(keyNanme, value);
			return this;
		}
		
		public Builder add(String keyName, Boolean value){
			intent.putExtra(keyName, value);
			return this;
		}
		
		public Builder add(String keyName, int value){
			intent.putExtra(keyName, value);
			return this;
		}
		
		public Builder add(String keyName, Serializable value) {
            intent.putExtra(keyName, value);
            return this;
        }
		
		public Intent toIntent(){
			return intent;
		}

	}
}
