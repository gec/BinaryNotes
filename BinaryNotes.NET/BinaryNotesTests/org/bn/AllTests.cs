/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
using System;
//UPGRADE_TODO: The type 'junit.framework.Test' could not be found. If it was not included in the conversion, there may be compiler issues. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1262'"
using Test = junit.framework.Test;
//UPGRADE_TODO: The type 'junit.framework.TestSuite' could not be found. If it was not included in the conversion, there may be compiler issues. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1262'"
using TestSuite = junit.framework.TestSuite;
//UPGRADE_TODO: The type 'junit.swingui.TestRunner' could not be found. If it was not included in the conversion, there may be compiler issues. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1262'"
using TestRunner = junit.swingui.TestRunner;
using DecoderTest = test.org.bn.coders.DecoderTest;
using EncoderTest = test.org.bn.coders.EncoderTest;
namespace test.org.bn
{
	
	public class AllTests
	{
		public static Test suite()
		{
			TestSuite suite;
			suite = new TestSuite("AllTests");
			suite.addTestSuite(typeof(EncoderTest));
			suite.addTestSuite(typeof(DecoderTest));
			return suite;
		}
		
		[STAThread]
		public static void  Main(System.String[] args)
		{
			System.String[] args2 = new System.String[]{"-noloading", "test.org.bn.AllTests"};
			TestRunner.main(args2);
		}
	}
}