package solution;

import java.util.ArrayList;
import java.util.HashMap;

import schedule.CourseBlock;
import schedule.CourseTime;

public class Solution{
	HashMap<String, CourseBlock> blocks = new HashMap<>();

	public void setBlocks(HashMap<String, CourseBlock> blocks) {
		this.blocks = blocks;
	}

	public HashMap<String, CourseBlock> getBlocks() {
		return blocks;
	}

	public Solution copy() {
		Solution copy = new Solution();
		HashMap<String, CourseBlock> blocksNew = new HashMap<>();

		for (CourseBlock block: blocks.values()){
			CourseBlock newBlock = new CourseBlock(
					block.getCourseName(), 
					block.getType(), 
					block.getSection(), 
					new CourseTime(block.getCourseTime().getDay(), block.getCourseTime().getBegin().getHourOfDay(), block.getCourseTime().getBegin().getMinuteOfHour(), 90));
			
			blocksNew.put(newBlock.getCourseName(), newBlock);
		}
		
		copy.setBlocks(blocksNew);
		
		return copy;
	}
}