# LinkedList

## Two Pointer

Two ways

- One slow-runner and the other fast-runner.
- One pointer starts from the beginning while the other pointer starts from the end.

Check out the loop invariant

Example 1 : Find cycle on a linked list

```cpp
/**
 * @param head head of a linked list
 * @return whether there is a cycle or not
 */
bool hasCycle(ListNode *head) {
  ListNode* slower = head;
  ListNode* faster = head;
  while (faster && faster->next && faster->next->next) {
    slower = slower->next;
    faster = faster->next->next;
    if (slower == faster) return true; // if there is a cycle, slower will follow faster one
  }
  return false;
}
```
