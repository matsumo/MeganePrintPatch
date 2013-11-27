/**
 * Megane Print Patch Copyright (C) 2013 matsumo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.matsumo.meganeprintpatch;

import java.util.Locale;

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class Patch implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static String MODULE_PATH = null;

	@Override
	public void initZygote(StartupParam startupParam) {
		MODULE_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
		if (resparam.packageName.equals("com.android.printspooler") && Locale.JAPAN.equals(Locale.getDefault())){
			resparam.res.setReplacement("com.android.printspooler", "array", "pdf_printer_media_sizes", modRes.fwd(R.array.pdf_printer_media_sizes));
		}
	}

}
