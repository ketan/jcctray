/*******************************************************************************
 *  Copyright 2007 Ketan Padegaonkar http://ketan.padegaonkar.name
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package net.sourceforge.jcctray.ui.settings.providers;

import junit.framework.TestCase;
import net.sourceforge.jcctray.model.DashBoardProject;
import net.sourceforge.jcctray.model.Host;

/**
 * @author Ketan Padegaonkar
 * 
 */
public class ProjectLabelProviderTest extends TestCase {

	private static final String		SOME_TIME		= "Some time";
	private static final String		PROJECT_NAME	= "myProject";
	private ProjectLabelProvider	labelProvider;
	private DashBoardProject		project;

	public void testGetsGreenImageOnSuccess() throws Exception {
		project.setLastBuildStatus(IProjectLabelConstants.SUCCESS);
		project.setActivity(IProjectLabelConstants.SLEEPING);
		assertSame(IProjectLabelConstants.GREEN_IMG, labelProvider.getImage(project));
	}

	public void testGetsRedImageOnFailure() throws Exception {
		project.setLastBuildStatus(IProjectLabelConstants.FAILURE);
		project.setActivity(IProjectLabelConstants.SLEEPING);
		assertSame(IProjectLabelConstants.RED_IMG, labelProvider.getImage(project));
	}

	public void testGetsOrangeImageOnFailureAndBuilding() throws Exception {
		project.setLastBuildStatus(IProjectLabelConstants.FAILURE);
		project.setActivity(IProjectLabelConstants.BUILDING);
		assertSame(IProjectLabelConstants.ORANGE_IMG, labelProvider.getImage(project));
	}

	public void testGetsYellowImageOnSuccessAndBuilding() throws Exception {
		project.setLastBuildStatus(IProjectLabelConstants.SUCCESS);
		project.setActivity(IProjectLabelConstants.BUILDING);
		assertSame(IProjectLabelConstants.YELLOW_IMG, labelProvider.getImage(project));
	}

	public void testGetsYellowImageOnUnknownAndBuilding() throws Exception {
		project.setLastBuildStatus(IProjectLabelConstants.UNKNOWN);
		project.setActivity(IProjectLabelConstants.BUILDING);
		assertSame(IProjectLabelConstants.YELLOW_IMG, labelProvider.getImage(project));
	}

	public void testGetsGrayImageOnAnyOtherState() throws Exception {
		assertSame(IProjectLabelConstants.GRAY_IMG, labelProvider.getImage(project));
	}

	public void testGetsImageOnZeroColumn() throws Exception {
		assertNotNull(labelProvider.getColumnImage(project, 0));
	}

	public void testDoesNotGetImageOnAnyOtherColumn() throws Exception {
		assertNull(labelProvider.getColumnImage(project, 1));
	}

	public void testGetTextReturnsProjectName() throws Exception {
		project.setName(PROJECT_NAME);
		assertEquals(PROJECT_NAME, labelProvider.getText(project));
	}

	public void testGetsProjectNameForZerothColumn() throws Exception {
		project.setName(PROJECT_NAME);
		assertEquals(PROJECT_NAME, labelProvider.getColumnText(project, 0));
	}

	public void testGetsProjectHostStringOnFirstColumn() throws Exception {
		project.setHost(new Host("MyHost", null));
		assertEquals("MyHost", labelProvider.getColumnText(project, 1));
	}

	public void testGetsActivityOnSecondColumn() throws Exception {
		project.setActivity(IProjectLabelConstants.BUILDING);
		assertEquals(IProjectLabelConstants.BUILDING, labelProvider.getColumnText(project, 2));
	}

	public void testGetsProjectDetailOnThirdColumn() throws Exception {
		project.setNextBuildTime(SOME_TIME);
		assertEquals("Next build check at: Some time", labelProvider.getColumnText(project, 3));

		project.setNextBuildTime(null);
		assertEquals(IProjectLabelConstants.WAITING_FOR_BUILD, labelProvider.getColumnText(project, 3));
	}

	public void testGetsLastBuildLabelOnFourthColumn() throws Exception {
		project.setLastBuildLabel(IProjectLabelConstants.FAILURE);
		assertEquals(IProjectLabelConstants.FAILURE, labelProvider.getColumnText(project, 4));
	}

	public void testGetsLastBuildLabelOnFifthColumn() throws Exception {
		project.setLastBuildTime(null);
		assertEquals("", labelProvider.getColumnText(project, 5));

		project.setLastBuildTime(SOME_TIME);
		assertEquals(SOME_TIME, labelProvider.getColumnText(project, 5));
	}

	public void testGetsEmptyStringOnAnyOtherColumn() throws Exception {
		assertEquals("", labelProvider.getColumnText(project, 8));
	}

	protected void setUp() throws Exception {
		this.labelProvider = new ProjectLabelProvider();
		this.project = new DashBoardProject();
	}
}
