/**
 * Created by JBA3353 on 30/01/2015.
 */
var debug = require('debug')('TalentMapLite:services:skills');

var skillSchema = require('../schemas/skills.js');

var skillsService =  {


    /*
     * Return array of skills object without their ID
     * This function is ideal for REST exposition
     */
    safeCleanSkills: function(skills) {
        if(skills == null) {
            return [];
        }

        var cleanedSkills = [];
        skills.forEach(function(skill) {
            cleanedSkills.push(skillsService.safeCleanSkill(skill));
        });
        return cleanedSkills;
    },

    /*
     * Return a skill object without his ID
     * This function is ideal for REST exposition
     */
    safeCleanSkill: function(skill) {
        if(skill == null) {
            return [];
        }

        var cleanedSkill = skill.toObject();
        return cleanedSkill;
    },



    /*
     * GET skills list.
     */
    listSkills: function(callback) {
        skillSchema.find({}, function(err, skills) {
            if(err) debug(err);
            callback(err, skills);
        });
    },



    /*
     * GET skills By Category.
     */
    getSkillsByCategory: function(categoryUid, callback) {
        skillSchema.find({category: categoryUid}, function(err, skills) {
            if(err) debug(err);
            callback(err, skills);
        });
    },

    /*
     * GET skill.
     */
    getSkill: function(skillName, callback) {
        skillSchema.findOne({name: skillName}, function(err, skill) {
            if(err) debug(err);
            callback(err, skill);
        });
    },


    /*
     * ADD skill if not exist.
     */
    addSkill: function(skillName, skillVolatile, categoryUid, callback) {

        var newSkill = skillSchema();
        newSkill.name = skillName;
        newSkill.volatile = skillVolatile;
        newSkill.category = categoryUid;

        skillSchema.update({name: skillName, category: categoryUid},
            newSkill.toObject(), {upsert: true}, function (err) {
                if(err) debug(err);
                callback(err, newSkill);
            });
    },


    /*
     * DELETE skill.
     */
    deleteSkill: function(skillName, callback) {
        skillSchema.remove({name: skillName}, function(err) {
            if(err) debug(err);
            callback(err, {});

        });
    }

};


module.exports = skillsService;

