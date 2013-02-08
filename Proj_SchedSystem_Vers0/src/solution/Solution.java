package solution;

import java.util.ArrayList;

import schedule.CourseBlock;
import schedule.CourseTime;

public class Solution{
	ArrayList<CourseBlock> blocks = new ArrayList<>();

	public void setBlocks(ArrayList<CourseBlock> blocks) {
		this.blocks = blocks;
	}

	public ArrayList<CourseBlock> getBlocks() {
		return blocks;
	}

	public Solution copy() {
		Solution copy = new Solution();
		ArrayList<CourseBlock> blocksNew = new ArrayList<>();

		for (CourseBlock block: blocks){
			CourseBlock newBlock = new CourseBlock(
					block.getCourseName(), 
					block.getType(), 
					block.getSection(), 
					new CourseTime(block.getCourseTime().getDay(), block.getCourseTime().getBegin().getHourOfDay(), block.getCourseTime().getBegin().getMinuteOfHour(), 90));
			
			blocksNew.add(newBlock);
		}
		
		copy.setBlocks(blocksNew);
		
		return copy;
	}
}