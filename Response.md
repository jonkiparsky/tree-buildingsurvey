Child classes: [RadioResponse](RadioResponse.md), [WrittenResponse](WrittenResponse.md)

# Introduction #
Response is the superclass for student responses to questions about the task. Since the radio-button questions are no longer part of the student task, it really only defines the WrittenResponse object, but changes to this class should not break RadioResponse, in case the radio questions are desired again.

The methods defined in this class are fairly simple but I should define them anyway.